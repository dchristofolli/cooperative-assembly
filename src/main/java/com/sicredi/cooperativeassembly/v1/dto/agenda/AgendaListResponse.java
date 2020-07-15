package com.sicredi.cooperativeassembly.v1.dto.agenda;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendaListResponse {
    @ApiModelProperty(notes = "Pautas para as reuniões")
    private List<AgendaResponse> list;
    @ApiModelProperty(notes = "Total de pautas cadastradas", example = "1")
    private Integer quantity;
}
