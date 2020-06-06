package com.sicredi.cooperativeassembly.mapper;

import com.sicredi.cooperativeassembly.data.entity.SessionEntity;
import com.sicredi.cooperativeassembly.model.session.SessionListResponse;
import com.sicredi.cooperativeassembly.model.session.SessionRequestModel;
import com.sicredi.cooperativeassembly.model.session.SessionResponseModel;
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
    public static SessionEntity mapModelToEntity(SessionRequestModel sessionRequestModel) {
        return SessionEntity.builder()
                .agendaId(sessionRequestModel.getAgendaId())
                .sessionCloseTime(Instant.now().plusSeconds(sessionRequestModel.getMinutesRemaining() * 60))
                .cpfAlreadyVoted(Collections.emptyList())
                .votes(Collections.emptyList())
                .build();
    }

    public static SessionResponseModel mapEntityToModel(SessionEntity sessionEntity) {
        return SessionResponseModel.builder()
                .sessionId(sessionEntity.getSessionId())
                .build();
    }

    public static SessionListResponse mapToSessionList(List<SessionEntity> sessionEntityList){
        List<SessionResponseModel> sessionResponseModels = sessionEntityList
                .parallelStream()
                .map(sessionEntity -> SessionResponseModel.builder()
                .sessionId(sessionEntity.getSessionId())
                .build()).collect(Collectors.toList());
        return SessionListResponse.builder()
                .list(sessionResponseModels)
                .quantity(sessionResponseModels.size())
                .build();
    }
}
