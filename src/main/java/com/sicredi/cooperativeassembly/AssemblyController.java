package com.sicredi.cooperativeassembly;

import com.sicredi.cooperativeassembly.model.AgendaRegistrationModel;
import com.sicredi.cooperativeassembly.model.AgendaResponseModel;
import com.sicredi.cooperativeassembly.service.SessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@Api(value = "Cooperative Assembly")
@RequestMapping(path = "/assembly/agenda")
public class AssemblyController {
    private SessionService sessionService;

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new agenda")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User successfully registered"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "An error occurred on the server")
    })
    @PostMapping
    public AgendaResponseModel createAgenda(@RequestBody AgendaRegistrationModel agendaRegistrationModel) {
        return sessionService.createAgenda(agendaRegistrationModel);
    }
}
