package com.sicredi.cooperativeassembly.v1.dto.session;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionListResponse {
    @ApiModelProperty(notes = "Sessões encontradas")
    private List<SessionResponse> list;
    @ApiModelProperty(notes = "Total de reuniões encontradas", example = "1")
    private Integer quantity;
}
