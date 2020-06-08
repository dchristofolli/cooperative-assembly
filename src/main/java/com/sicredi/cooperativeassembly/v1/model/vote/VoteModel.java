package com.sicredi.cooperativeassembly.v1.model.vote;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@lombok.Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class VoteModel {
    @ApiModelProperty(notes = "CPF do cooperado", example = "01063682061")
    @Size(min = 11, max = 11, message = "Digite o CPF (Somente números)")
    private String cpf;

    @ApiModelProperty(notes = "ID da sessão que será votada", example = "5edd42536877d815f01a0a4a")
    @Size(min = 1, message = "O ID deve ser preenchido")
    private String sessionId;

    @ApiModelProperty(notes = "Voto", example = "s", allowableValues = "'s' ou 'n'")
    @NotBlank
    @Size(min = 1, max = 1, message = "Digite 1 caractere. As opções são 's' ou 'n'")
    private String option;
}
