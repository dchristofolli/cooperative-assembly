package com.sicredi.cooperativeassembly.model.agenda;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendaRequest {
    String subject;
    String description;
}
