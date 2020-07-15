package com.sicredi.cooperativeassembly.v1.dto.session;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@lombok.Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class SessionRequest {
    @ApiModelProperty(notes = "Identificador da pauta que será votada na sessão", example = "5edd42536877d815f01a0a4a")
    @NotBlank(message = "ID deve ser preenchido")
    private String agendaId;

    @ApiModelProperty(notes = "Duração da sessão que será iniciada", example = "2")
    private Long minutesRemaining;
}
