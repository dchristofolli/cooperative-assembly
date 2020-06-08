package com.sicredi.cooperativeassembly.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Builder
@AllArgsConstructor
@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
class ErrorModel {
    @ApiModelProperty(notes = "Mensagem que será exibida no corpo da resposta, em caso de erro",
            example = "Invalid form")
    String message;

    @ApiModelProperty(notes = "Classe da exceção que foi chamada",
            example = "org.springframework.web.bind.MethodArgumentNotValidException")
    String error;

    @ApiModelProperty(notes = "Status http da resposta",
            example = "BAD_REQUEST")
    HttpStatus status;

    @ApiModelProperty(notes = "Status http da resposta",
            example = "\"formErrors\": {\n" +
                    "    \"description\": \"O campo deve ser preenchido\"\n" +
                    "  }")
    Map<String, String> formErrors;
}
