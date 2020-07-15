package com.sicredi.cooperativeassembly.v1.facade;

import com.sicredi.cooperativeassembly.exception.ApiException;
import com.sicredi.cooperativeassembly.v1.service.*;
import com.sicredi.cooperativeassembly.v1.mapper.SessionMapper;
import com.sicredi.cooperativeassembly.v1.dto.agenda.AgendaListResponse;
import com.sicredi.cooperativeassembly.v1.dto.agenda.AgendaRequest;
import com.sicredi.cooperativeassembly.v1.dto.agenda.AgendaResponse;
import com.sicredi.cooperativeassembly.v1.dto.session.SessionListResponse;
import com.sicredi.cooperativeassembly.v1.dto.session.SessionRequest;
import com.sicredi.cooperativeassembly.v1.dto.session.SessionResponse;
import com.sicredi.cooperativeassembly.v1.dto.session.SessionResult;
import com.sicredi.cooperativeassembly.v1.dto.vote.VoteModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.sicredi.cooperativeassembly.v1.mapper.AgendaMapper.mapEntityToResponse;
import static com.sicredi.cooperativeassembly.v1.mapper.AgendaMapper.mapToAgendaList;
import static com.sicredi.cooperativeassembly.v1.mapper.SessionMapper.mapEntityToModel;
import static com.sicredi.cooperativeassembly.v1.mapper.SessionMapper.mapModelToEntity;

@Component
@AllArgsConstructor
@Slf4j
public class AssemblyFacade {
    private final AgendaService agendaService;
    private final SessionService sessionService;
    private final CpfService cpfService;
    private final KafkaService kafkaService;
    private final EmailService emailService;

    public AgendaResponse createAgenda(AgendaRequest agendaRequest) {
        return mapEntityToResponse(agendaService.save(agendaRequest));
    }

    public SessionResponse createVotingSession(SessionRequest sessionRequest) {
        if (!agendaService.existsById(sessionRequest.getAgendaId()))
            throw new ApiException("Invalid Agenda Id", HttpStatus.BAD_REQUEST);
        if (sessionRequest.getMinutesRemaining().equals(0L) || sessionRequest.getMinutesRemaining() == null)
            sessionRequest.setMinutesRemaining(1L);
        return mapEntityToModel(sessionService.createVotingSession(mapModelToEntity(sessionRequest)));
    }

    public SessionListResponse findAllOpenSessions() {
        return SessionMapper.mapToSessionList(sessionService.findAllOpenSessions());
    }

    public VoteModel vote(VoteModel voteModel) {
        if (!sessionService.sessionIsActive(voteModel.getSessionId()))
            throw new ApiException("Session is not active", HttpStatus.NOT_FOUND);
        if (!cpfService.cpfIsAbleToVote(voteModel.getCpf()))
            throw new ApiException("Unable to vote", HttpStatus.UNAUTHORIZED);
        if (sessionService.alreadyVotedOnThisSession(voteModel))
            throw new ApiException("User already voted on this session", HttpStatus.FORBIDDEN);
        return sessionService.vote(voteModel);
    }

    public SessionResult sessionResult(String sessionId) {
        if (sessionService.sessionIsActive(sessionId)) {
            throw new ApiException("The session is still active", HttpStatus.FORBIDDEN);
        }
        return sessionService.checkSessionResult(sessionId);
    }

    @Scheduled(fixedDelay = 1000)
    public void sendResults() {
        sessionService.getSessionResults()
                .parallelStream()
                .filter(result ->
                        Objects.equals(sessionService.findSessionById(result.getSessionId())
                                .getMessageAlreadySent(), "N"))
                .map(session -> {
                    emailService.mailSend(session);
                    return kafkaService.makeRecord(session);
                })
                .forEach(kafkaService::send);
        sessionService.findAllClosedSessions()
                .forEach(sessionService::setMessageAlreadySent);
    }

    public AgendaListResponse findAllAgendas() {
        return mapToAgendaList(agendaService.findAll());
    }
}
