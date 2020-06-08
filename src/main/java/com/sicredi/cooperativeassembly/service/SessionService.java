package com.sicredi.cooperativeassembly.service;

import com.sicredi.cooperativeassembly.data.entity.SessionEntity;
import com.sicredi.cooperativeassembly.exception.ApiException;
import com.sicredi.cooperativeassembly.v1.model.session.SessionResult;
import com.sicredi.cooperativeassembly.v1.model.vote.VoteModel;
import com.sicredi.cooperativeassembly.data.repository.SessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionEntity findSessionById(String sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ApiException("Session not found", HttpStatus.NOT_FOUND));
    }

    public SessionEntity createVotingSession(SessionEntity sessionEntity) {
        return sessionRepository.save(sessionEntity);
    }

    public Boolean sessionIsActive(String sessionId) {
        return findSessionById(sessionId).getSessionCloseTime().isAfter(Instant.now());
    }

    public List<SessionEntity> findAllOpenSessions() {
        List<SessionEntity> activeSessions = sessionRepository.findAll().parallelStream()
                .filter(a -> a.getSessionCloseTime().isAfter(Instant.now()))
                .collect(Collectors.toList());
        if (activeSessions.isEmpty()) {
            throw new ApiException("There are no active sessions", HttpStatus.NOT_FOUND);
        }
        return activeSessions;
    }

    public List<SessionEntity> findAllClosedSessions() {
        List<SessionEntity> closedSessions = sessionRepository.findAll().parallelStream()
                .filter(a -> a.getSessionCloseTime().isBefore(Instant.now()))
                .collect(Collectors.toList());
        if (closedSessions.isEmpty()) {
            throw new ApiException("There are no closed sessions", HttpStatus.NOT_FOUND);
        }
        return closedSessions;
    }


    public VoteModel vote(VoteModel voteModel) {
        SessionEntity sessionEntity = findSessionById(voteModel.getSessionId());
        List<String> votes = sessionEntity.getVotes();
        List<String> cpf = sessionEntity.getCpfAlreadyVoted();
        votes.add(voteModel.getOption().toUpperCase());
        cpf.add(voteModel.getCpf());
        sessionEntity.setVotes(votes);
        sessionEntity.setCpfAlreadyVoted(cpf);
        sessionRepository.save(sessionEntity);
        return voteModel;
    }

    public Boolean alreadyVotedOnThisSession(VoteModel voteModel) {
        return findSessionById(voteModel.getSessionId()).getCpfAlreadyVoted().contains(voteModel.getCpf());
    }


    public SessionResult checkSessionResult(String sessionId) {
        String agendaId = findSessionById(sessionId).getAgendaId();
        List<String> votes = findSessionById(sessionId).getVotes();
        return SessionResult.builder()
                .sessionId(sessionId)
                .agendaId(agendaId)
                .favor(votes.parallelStream().filter(v -> v.equals("S")).count())
                .against(votes.parallelStream().filter(v -> v.equals("N")).count())
                .total(votes.size())
                .build();
    }

    public List<SessionResult> getSessionResults() {
        List<SessionResult> results = findAllClosedSessions()
                .parallelStream()
                .map(s -> checkSessionResult(s.getSessionId()))
                .collect(Collectors.toList());
        if(results.isEmpty()){
            throw new ApiException("There are no results", HttpStatus.NOT_FOUND);
        }
        return results;
    }

    public void setMessageAlreadySent(SessionEntity sessionEntity) {
        sessionEntity.setMessageAlreadySent("s");
        sessionRepository.save(sessionEntity);
    }
}
