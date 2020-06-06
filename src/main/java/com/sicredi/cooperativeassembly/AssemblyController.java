package com.sicredi.cooperativeassembly;

import com.sicredi.cooperativeassembly.entity.AgendaEntity;
import com.sicredi.cooperativeassembly.model.AgendaRegistrationModel;
import com.sicredi.cooperativeassembly.model.AgendaResponseModel;
import com.sicredi.cooperativeassembly.model.VotationSessionModel;
import com.sicredi.cooperativeassembly.service.SessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Api(value = "Cooperative Assembly")
@RequestMapping(path = "/assembly/agenda")
public class AssemblyController {
    private SessionService sessionService;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new agenda")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Agenda successfully registered"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "An error occurred on the server")
    })
    @PostMapping
    public AgendaResponseModel createAgenda(@RequestBody AgendaRegistrationModel agendaRegistrationModel) {
        return sessionService.createAgenda(agendaRegistrationModel);
    }

    @ApiOperation("Opens a voting session, receiving the agenda id and the duration of the session. " +
            "Default duration: 1 minute")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Agenda found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Bad server")
    })
    @PatchMapping
    public void openSession(String agendaId, Long timeInMinutes) {
        sessionService.openVotingSession(agendaId, timeInMinutes);
    }

    @ApiOperation("Finds all agendas that have an active session")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sessions found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Bad server")
    })
    @GetMapping
    public List<AgendaEntity> findAllOpenSessions(){
        return sessionService.findAllOpenSessions();
    }
}
