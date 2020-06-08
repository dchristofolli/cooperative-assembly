package com.sicredi.cooperativeassembly.v1.controller;

import com.sicredi.cooperativeassembly.v1.facade.AssemblyFacade;
import com.sicredi.cooperativeassembly.v1.model.agenda.AgendaRequest;
import com.sicredi.cooperativeassembly.v1.model.agenda.AgendaResponse;
import com.sicredi.cooperativeassembly.v1.model.session.SessionRequest;
import com.sicredi.cooperativeassembly.v1.model.session.SessionResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/assembly/admin")
public class AdminController {
    private final AssemblyFacade assemblyFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new agenda")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Agenda successfully registered"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "An error occurred on the server")
    })
    @PostMapping("/agenda")
    public AgendaResponse createAgenda(@Valid @RequestBody AgendaRequest agendaRequest) {
        return assemblyFacade.createAgenda(agendaRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a voting session, receiving the agenda id and the duration of the session.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Open session"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Bad server")
    })
    @PostMapping("/session")
    public SessionResponse createSession(@RequestBody SessionRequest sessionRequest) {
        return assemblyFacade.createVotingSession(sessionRequest);
    }
}
