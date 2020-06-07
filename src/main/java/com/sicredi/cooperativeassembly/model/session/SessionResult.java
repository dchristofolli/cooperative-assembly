package com.sicredi.cooperativeassembly.model.session;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class SessionResult {
    String sessionId;
    String agendaId;
    Long favor;
    Long against;
    Integer total;
}
