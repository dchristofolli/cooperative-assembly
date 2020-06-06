package com.sicredi.cooperativeassembly.facade;

import com.sicredi.cooperativeassembly.entity.AgendaEntity;
import com.sicredi.cooperativeassembly.exception.ApiException;
import com.sicredi.cooperativeassembly.model.AgendaRegistrationModel;
import com.sicredi.cooperativeassembly.model.AgendaResponseModel;
import com.sicredi.cooperativeassembly.model.ResultModel;
import com.sicredi.cooperativeassembly.model.VotingModel;
import com.sicredi.cooperativeassembly.service.SessionService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.sicredi.cooperativeassembly.mapper.AgendaMapper.mapEntityToResponse;

@Component
@AllArgsConstructor
public class AssemblyFacade {
    private final SessionService sessionService;

    public AgendaResponseModel createAgenda(AgendaRegistrationModel agendaRegistrationModel) {
        return mapEntityToResponse(sessionService.createAgenda(agendaRegistrationModel));
    }

    public void openVotingSession(String agendaId, Long timeInMinutes) {
        sessionService.openVotingSession(agendaId, timeInMinutes);
    }

    public List<AgendaEntity> findAllOpenSessions() {
        return sessionService.findAllOpenSessions();
    }

    public void vote(VotingModel votingModel) {
        if(sessionService.cpfAlreadyVotedOnThisSession(votingModel))
            throw new ApiException("User already voted on this session", HttpStatus.FORBIDDEN);
        if(!sessionService.sessionIsOpen(votingModel.getAgendaId()))
                throw new ApiException("This session is not active", HttpStatus.NOT_FOUND);
        sessionService.vote(votingModel);
    }

    public ResultModel votationResult(String agendaId){
        if(sessionService.sessionIsOpen(agendaId)){
            throw new ApiException("The session is still active", HttpStatus.FORBIDDEN);
        }
        return sessionService.votationResult(agendaId);
    }
}
