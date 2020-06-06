package com.sicredi.cooperativeassembly.mapper;

import com.sicredi.cooperativeassembly.entity.SessionEntity;
import com.sicredi.cooperativeassembly.model.SessionRequestModel;
import com.sicredi.cooperativeassembly.model.SessionResponseModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Collections;

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
}
