package com.sicredi.cooperativeassembly.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Agenda")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgendaEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String subject;
    private String description;
    private List<String> votes;
    private List<String> cpfAlreadyVoted;
    private Instant sessionCloseTime;
}
