package com.sicredi.cooperativeassembly.v1.dto.agenda;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class AgendaResponse {
    @ApiModelProperty(notes = "Identificador da pauta", example = "5edd42536877d815f01a0a4a")
    String id;

    @ApiModelProperty(notes = "Assunto da pauta", example = "Mais reuniões")
    String subject;
}
