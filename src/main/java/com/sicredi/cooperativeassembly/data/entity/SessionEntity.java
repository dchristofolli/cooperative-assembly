package com.sicredi.cooperativeassembly.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sicredi.cooperativeassembly.model.session.SessionResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Session")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionEntity {
    @Id
    private String sessionId;
    private String agendaId;
    private List<String> votes;
    private List<String> cpfAlreadyVoted;
    private Instant sessionCloseTime;
    @JsonIgnore
    String messageAlreadySent="n";
}
