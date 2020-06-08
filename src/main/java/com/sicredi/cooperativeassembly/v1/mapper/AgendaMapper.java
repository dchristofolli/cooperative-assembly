package com.sicredi.cooperativeassembly.v1.mapper;

import com.sicredi.cooperativeassembly.data.entity.AgendaEntity;
import com.sicredi.cooperativeassembly.v1.model.agenda.AgendaListResponse;
import com.sicredi.cooperativeassembly.v1.model.agenda.AgendaRequest;
import com.sicredi.cooperativeassembly.v1.model.agenda.AgendaResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AgendaMapper {
    public static AgendaEntity mapAgendaToEntity(AgendaRequest agendaRequest) {
        return AgendaEntity.builder()
                .subject(agendaRequest.getSubject())
                .description(agendaRequest.getDescription())
                .build();
    }

    public static AgendaResponse mapEntityToResponse(AgendaEntity agendaEntity) {
        return AgendaResponse.builder()
                .id(agendaEntity.getId())
                .subject(agendaEntity.getSubject())
                .build();
    }

    public static AgendaListResponse mapToAgendaList(List<AgendaEntity> agendaEntityList) {
        List<AgendaResponse> response = agendaEntityList.parallelStream()
                .map(agenda -> AgendaResponse.builder()
                        .id(agenda.getId())
                        .subject(agenda.getSubject())
                        .build()).collect(Collectors.toList());
        return AgendaListResponse.builder()
                .list(response)
                .quantity(response.size())
                .build();
    }
}
