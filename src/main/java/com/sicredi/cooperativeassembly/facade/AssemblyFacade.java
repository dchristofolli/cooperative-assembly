package com.sicredi.cooperativeassembly.facade;

import com.sicredi.cooperativeassembly.entity.SessionEntity;
import com.sicredi.cooperativeassembly.exception.ApiException;
import com.sicredi.cooperativeassembly.model.*;
import com.sicredi.cooperativeassembly.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.sicredi.cooperativeassembly.mapper.AgendaMapper.mapEntityToResponse;
import static com.sicredi.cooperativeassembly.mapper.SessionMapper.mapEntityToModel;
import static com.sicredi.cooperativeassembly.mapper.SessionMapper.mapModelToEntity;

@Component
@AllArgsConstructor
public class AssemblyFacade {
    private final SessionService sessionService;

    public AgendaResponseModel createAgenda(AgendaRegistrationModel agendaRegistrationModel) {
        return mapEntityToResponse(sessionService.createAgenda(agendaRegistrationModel));
    }

    public SessionResponseModel createVotingSession(SessionRequestModel sessionRequestModel) {
        if(!sessionService.agendaExistsById(sessionRequestModel.getAgendaId()))
            throw new ApiException("Invalid Agenda Id", HttpStatus.BAD_REQUEST);
        return mapEntityToModel(sessionService.createVotingSession(mapModelToEntity(sessionRequestModel)));
    }

    public List<SessionEntity> findAllOpenSessions() {
        return sessionService.findAllOpenSessions();
    }

    public void vote(VotingModel votingModel) {
        if(!sessionService.sessionIsActive(votingModel.getSessionId()))
            throw new ApiException("Session is not active", HttpStatus.NOT_FOUND);
        if(sessionService.alreadyVotedOnThisSession(votingModel))
            throw new ApiException("User already voted on this session", HttpStatus.FORBIDDEN);
        if(!sessionService.sessionIsActive(votingModel.getSessionId()))
                throw new ApiException("This session is not active", HttpStatus.NOT_FOUND);
        sessionService.vote(votingModel);
    }

    public ResultModel votationResult(String agendaId){
        if(sessionService.sessionIsActive(agendaId)){
            throw new ApiException("The session is still active", HttpStatus.FORBIDDEN);
        }
        return sessionService.votationResult(agendaId);
    }
}
