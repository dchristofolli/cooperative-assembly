package com.sicredi.cooperativeassembly.controller;

import com.sicredi.cooperativeassembly.facade.AssemblyFacade;
import com.sicredi.cooperativeassembly.model.agenda.AgendaListResponse;
import com.sicredi.cooperativeassembly.model.session.SessionListResponse;
import com.sicredi.cooperativeassembly.model.session.SessionResult;
import com.sicredi.cooperativeassembly.model.vote.VoteModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@Api(value = "Cooperative Assembly")
@RequestMapping(path = "/assembly/members")
public class AssemblyController {
    private final AssemblyFacade assemblyFacade;

    @ApiOperation("Find all agendas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sessions found"),
            @ApiResponse(code = 500, message = "Bad server")
    })
    @GetMapping("agenda/all")
    public AgendaListResponse findAllAgendas() {
        return assemblyFacade.findAllAgendas();
    }

    @ApiOperation("Find all open sessions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sessions found"),
            @ApiResponse(code = 500, message = "Bad server")
    })
    @GetMapping("session/open-sessions")
    public SessionListResponse findAllOpenSessions() {
        return assemblyFacade.findAllOpenSessions();
    }

    @ApiOperation("Allows a member to vote on an agenda that has an active session")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Vote successfully registered"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "An error occurred on the server")
    })
    @PostMapping("/vote")
    public VoteModel vote(@RequestBody VoteModel voteModel) {
        return assemblyFacade.vote(voteModel);
    }

    @ApiOperation("Displays the result of closed polls")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Results found"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Bad server")
    })
    @GetMapping("/session/results")
    public SessionResult sessionResult(String sessionId) {
        return assemblyFacade.sessionResult(sessionId);
    }
}
