package com.sicredi.cooperativeassembly.v1.dto.session;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class SessionResult {
    @ApiModelProperty(notes = "Identificador da sessão", example = "5edd42536877d815f01a0a4a")
    String sessionId;

    @ApiModelProperty(notes = "Identificador da pauta", example = "5edd42536877d815f01a0a4a")
    String agendaId;

    @ApiModelProperty(notes = "Quantidade de votos a favor", example = "5")
    Long favor;

    @ApiModelProperty(notes = "Quantidade de votos contra", example = "0")
    Long against;

    @ApiModelProperty(notes = "Total de votos", example = "5")
    Integer total;
}
