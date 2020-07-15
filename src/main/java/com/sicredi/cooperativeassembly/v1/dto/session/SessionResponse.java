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
public class SessionResponse {
    @ApiModelProperty(notes = "Identificador da sessão que foi iniciada", example = "5edd42536877d815f01a0a4a")
    private String sessionId;
}
