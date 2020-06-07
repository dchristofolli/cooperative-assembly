package com.sicredi.cooperativeassembly.facade;

import com.sicredi.cooperativeassembly.exception.ApiException;
import com.sicredi.cooperativeassembly.mapper.SessionMapper;
import com.sicredi.cooperativeassembly.model.agenda.AgendaListResponse;
import com.sicredi.cooperativeassembly.model.agenda.AgendaRequest;
import com.sicredi.cooperativeassembly.model.agenda.AgendaResponse;
import com.sicredi.cooperativeassembly.model.session.SessionListResponse;
import com.sicredi.cooperativeassembly.model.session.SessionRequest;
import com.sicredi.cooperativeassembly.model.session.SessionResponse;
import com.sicredi.cooperativeassembly.model.session.SessionResult;
import com.sicredi.cooperativeassembly.model.vote.VoteModel;
import com.sicredi.cooperativeassembly.service.AgendaService;
import com.sicredi.cooperativeassembly.service.CpfService;
import com.sicredi.cooperativeassembly.service.MessageService;
import com.sicredi.cooperativeassembly.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.sicredi.cooperativeassembly.mapper.AgendaMapper.mapEntityToResponse;
import static com.sicredi.cooperativeassembly.mapper.AgendaMapper.mapToAgendaList;
import static com.sicredi.cooperativeassembly.mapper.SessionMapper.mapEntityToModel;
import static com.sicredi.cooperativeassembly.mapper.SessionMapper.mapModelToEntity;

@Component
@AllArgsConstructor
public class AssemblyFacade {
    private final AgendaService agendaService;
    private final SessionService sessionService;
    private final CpfService cpfService;
    private final MessageService messageService;

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
                                .getMessageAlreadySent(), "n"))
                .map(messageService::makeRecord)
                .forEach(messageService::send);
        sessionService.findAllClosedSessions()
                .forEach(sessionService::setMessageAlreadySent);
    }

    public AgendaListResponse findAllAgendas() {
        return mapToAgendaList(agendaService.findAll());
    }
}
