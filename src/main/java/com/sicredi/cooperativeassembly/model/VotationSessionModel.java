package com.sicredi.cooperativeassembly.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class VotationSessionModel {
    private String agendaId;
    private String agendaSubject;
    private String agendaDescription;
}
