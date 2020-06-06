package com.sicredi.cooperativeassembly.service;

import com.sicredi.cooperativeassembly.entity.AgendaEntity;
import com.sicredi.cooperativeassembly.entity.SessionEntity;
import com.sicredi.cooperativeassembly.exception.ApiException;
import com.sicredi.cooperativeassembly.model.AgendaRegistrationModel;
import com.sicredi.cooperativeassembly.model.ResultModel;
import com.sicredi.cooperativeassembly.model.VotingModel;
import com.sicredi.cooperativeassembly.repository.AgendaRepository;
import com.sicredi.cooperativeassembly.repository.SessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.sicredi.cooperativeassembly.mapper.AgendaMapper.mapAgendaToEntity;

@Service
@AllArgsConstructor
@EnableScheduling
@Slf4j
public class SessionService {
    private final AgendaRepository agendaRepository;
    private final SessionRepository sessionRepository;

    public AgendaEntity createAgenda(AgendaRegistrationModel agendaRegistrationModel) {
        return agendaRepository.save(mapAgendaToEntity(agendaRegistrationModel));
    }

    public Boolean agendaExistsById(String agendaId){
        return agendaRepository.existsById(agendaId);
    }

    public AgendaEntity findAgendaById(String agendaId) {
        return agendaRepository.findById(agendaId)
                .orElseThrow(() -> new ApiException("Agenda not found", HttpStatus.NOT_FOUND));
    }

    public SessionEntity findSessionById(String sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ApiException("Session not found", HttpStatus.NOT_FOUND));
    }

    public SessionEntity createVotingSession(SessionEntity sessionEntity) {
        long duration = 60L;
        if (sessionEntity.getSessionCloseTime() != null)
            sessionEntity.setSessionCloseTime(Instant.now().plusSeconds(duration));
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

    public void vote(VotingModel votingModel) {
        SessionEntity sessionEntity = findSessionById(votingModel.getSessionId());
        List<String> votes = sessionEntity.getVotes();
        List<String> cpf = sessionEntity.getCpfAlreadyVoted();
        votes.add(votingModel.getOption().toUpperCase());
        cpf.add(votingModel.getCpf());
        sessionEntity.setVotes(votes);
        sessionEntity.setCpfAlreadyVoted(cpf);
        sessionRepository.save(sessionEntity);
    }

    public Boolean alreadyVotedOnThisSession(VotingModel votingModel) {
        return findSessionById(votingModel.getSessionId()).getCpfAlreadyVoted().contains(votingModel.getCpf());
    }

    public ResultModel votationResult(String sessionId) {
        String agendaId = findSessionById(sessionId).getAgendaId();
        List<String> votes = findSessionById(sessionId).getVotes();
        return ResultModel.builder()
                .sessionId(sessionId)
                .agendaId(agendaId)
                .favor(votes.parallelStream().filter(v -> v.equals("S")).count())
                .against(votes.parallelStream().filter(v -> v.equals("N")).count())
                .total(votes.size())
                .build();
    }
}
