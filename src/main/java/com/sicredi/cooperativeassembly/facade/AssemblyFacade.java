package com.sicredi.cooperativeassembly.facade;

import com.sicredi.cooperativeassembly.exception.ApiException;
import com.sicredi.cooperativeassembly.mapper.SessionMapper;
import com.sicredi.cooperativeassembly.model.agenda.AgendaListResponse;
import com.sicredi.cooperativeassembly.model.agenda.AgendaRegistrationModel;
import com.sicredi.cooperativeassembly.model.agenda.AgendaResponseModel;
import com.sicredi.cooperativeassembly.model.session.SessionListResponse;
import com.sicredi.cooperativeassembly.model.session.SessionRequestModel;
import com.sicredi.cooperativeassembly.model.session.SessionResponseModel;
import com.sicredi.cooperativeassembly.model.session.SessionResultModel;
import com.sicredi.cooperativeassembly.model.vote.VoteModel;
import com.sicredi.cooperativeassembly.service.AgendaService;
import com.sicredi.cooperativeassembly.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.sicredi.cooperativeassembly.mapper.AgendaMapper.mapEntityToResponse;
import static com.sicredi.cooperativeassembly.mapper.AgendaMapper.mapToAgendaList;
import static com.sicredi.cooperativeassembly.mapper.SessionMapper.mapEntityToModel;
import static com.sicredi.cooperativeassembly.mapper.SessionMapper.mapModelToEntity;

@Component
@AllArgsConstructor
public class AssemblyFacade {
    private final AgendaService agendaService;
    private final SessionService sessionService;

    public AgendaResponseModel createAgenda(AgendaRegistrationModel agendaRegistrationModel) {
        return mapEntityToResponse(agendaService.save(agendaRegistrationModel));
    }

    public SessionResponseModel createVotingSession(SessionRequestModel sessionRequestModel) {
        if (!agendaService.existsById(sessionRequestModel.getAgendaId()))
            throw new ApiException("Invalid Agenda Id", HttpStatus.BAD_REQUEST);
        if (sessionRequestModel.getMinutesRemaining().equals(0L) || sessionRequestModel.getMinutesRemaining() == null)
            sessionRequestModel.setMinutesRemaining(1L);
        return mapEntityToModel(sessionService.createVotingSession(mapModelToEntity(sessionRequestModel)));
    }

    public SessionListResponse findAllOpenSessions() {
        return SessionMapper.mapToSessionList(sessionService.findAllOpenSessions());
    }

    public VoteModel vote(VoteModel voteModel) {
        if (!sessionService.sessionIsActive(voteModel.getSessionId()))
            throw new ApiException("Session is not active", HttpStatus.NOT_FOUND);
        if (sessionService.alreadyVotedOnThisSession(voteModel))
            throw new ApiException("User already voted on this session", HttpStatus.FORBIDDEN);
        if (!sessionService.sessionIsActive(voteModel.getSessionId()))
            throw new ApiException("This session is not active", HttpStatus.NOT_FOUND);
        return sessionService.vote(voteModel);
    }

    public SessionResultModel sessionResult(String agendaId) {
        if (sessionService.sessionIsActive(agendaId)) {
            throw new ApiException("The session is still active", HttpStatus.FORBIDDEN);
        }
        return sessionService.votationResult(agendaId);
    }

    public AgendaListResponse findAllAgendas() {
        return mapToAgendaList(agendaService.findAll());
    }
}
