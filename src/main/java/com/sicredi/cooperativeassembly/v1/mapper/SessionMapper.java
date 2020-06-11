package com.sicredi.cooperativeassembly.v1.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sicredi.cooperativeassembly.data.entity.SessionEntity;
import com.sicredi.cooperativeassembly.v1.model.session.SessionListResponse;
import com.sicredi.cooperativeassembly.v1.model.session.SessionRequest;
import com.sicredi.cooperativeassembly.v1.model.session.SessionResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SessionMapper {
    public static SessionEntity mapModelToEntity(SessionRequest sessionRequest) {
        return SessionEntity.builder()
                .agendaId(sessionRequest.getAgendaId())
                .sessionCloseTime(Instant.now().plusSeconds(sessionRequest.getMinutesRemaining() * 60))
                .cpfAlreadyVoted(Collections.emptyList())
                .votes(Collections.emptyList())
                .build();
    }

    public static SessionResponse mapEntityToModel(SessionEntity sessionEntity) {
        return SessionResponse.builder()
                .sessionId(sessionEntity.getSessionId())
                .build();
    }

    public static SessionListResponse mapToSessionList(List<SessionEntity> sessionEntityList){
        List<SessionResponse> sessionResponses = sessionEntityList
                .parallelStream()
                .map(sessionEntity -> SessionResponse.builder()
                .sessionId(sessionEntity.getSessionId())
                .build()).collect(Collectors.toList());
        return SessionListResponse.builder()
                .list(sessionResponses)
                .quantity(sessionResponses.size())
                .build();
    }
}
