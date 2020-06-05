package com.sicredi.cooperativeassembly.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Agenda")
public class AgendaEntity {
    @Id
    private String id;

    private String subject;

    private String description;

    private Long votesInFavor;

    private Long votesAgainst;

    private Character openSession;
}
