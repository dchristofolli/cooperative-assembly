package com.sicredi.cooperativeassembly;

import com.sicredi.cooperativeassembly.entity.AgendaEntity;
import com.sicredi.cooperativeassembly.facade.AssemblyFacade;
import com.sicredi.cooperativeassembly.model.AgendaRegistrationModel;
import com.sicredi.cooperativeassembly.model.AgendaResponseModel;
import com.sicredi.cooperativeassembly.model.ResultModel;
import com.sicredi.cooperativeassembly.model.VotingModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@Api(value = "Cooperative Assembly")
@RequestMapping(path = "/assembly/agenda")
public class AssemblyController {
    private final AssemblyFacade assemblyFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new agenda")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Agenda successfully registered"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "An error occurred on the server")
    })
    @PostMapping
    public AgendaResponseModel createAgenda(@Valid @RequestBody AgendaRegistrationModel agendaRegistrationModel) {
        return assemblyFacade.createAgenda(agendaRegistrationModel);
    }

    @ApiOperation("Opens a voting session, receiving the agenda id and the duration of the session. " +
            "Default duration: 1 minute")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Agenda found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Bad server")
    })
    @PatchMapping("/session/{agendaId}/open")
    public void openSession(@PathVariable String agendaId, Long timeInMinutes) {
        assemblyFacade.openVotingSession(agendaId, timeInMinutes);
    }

    @ApiOperation("Finds all agendas that have an active session")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sessions found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Bad server")
    })
    @GetMapping("/all-open-sessions")
    public List<AgendaEntity> findAllOpenSessions() {
        return assemblyFacade.findAllOpenSessions();
    }

    @ApiOperation("Allows a member to vote on an agenda that has an active session")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Vote successfully registered"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "An error occurred on the server")
    })
    @PostMapping("/vote/")
    public void vote(@RequestBody VotingModel votingModel) {
        assemblyFacade.vote(votingModel);
    }

    @ApiOperation("Displays the result of closed polls")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Results found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Bad server")
    })
    @GetMapping("/votation/results")
    //TODO retornar todas as votações encerradas se o ID estiver em branco
    public ResultModel votationResult(String agendaId){
        return assemblyFacade.votationResult(agendaId);
    }
}
