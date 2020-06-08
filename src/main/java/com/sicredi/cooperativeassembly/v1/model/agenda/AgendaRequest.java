package com.sicredi.cooperativeassembly.v1.model.agenda;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendaRequest {
    @ApiModelProperty(notes = "Assunto da pauta", example = "Mais reuniões" ,required = true)
    @NotBlank(message = "O campo deve ser preenchido")
    @Size(min = 5, max = 32, message = "Deve ter entre 5 e 32 caracteres")
    String subject;

    @ApiModelProperty(notes = "Descrição da pauta", example = "Devemos fazer mais reuniões?", required = true)
    @NotBlank(message = "O campo deve ser preenchido")
    @Size(min = 16, message = "Deve ter pelo menos 16 caracteres")
    String description;
}
