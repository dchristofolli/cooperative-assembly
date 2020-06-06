package com.sicredi.cooperativeassembly.mapper;

import com.sicredi.cooperativeassembly.entity.AgendaEntity;
import com.sicredi.cooperativeassembly.model.agenda.AgendaListResponse;
import com.sicredi.cooperativeassembly.model.agenda.AgendaRegistrationModel;
import com.sicredi.cooperativeassembly.model.agenda.AgendaResponseModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AgendaMapper {
    public static AgendaEntity mapAgendaToEntity(AgendaRegistrationModel agendaRegistrationModel) {
        return AgendaEntity.builder()
                .subject(agendaRegistrationModel.getSubject())
                .description(agendaRegistrationModel.getDescription())
                .build();
    }

    public static AgendaResponseModel mapEntityToResponse(AgendaEntity agendaEntity) {
        return AgendaResponseModel.builder()
                .id(agendaEntity.getId())
                .subject(agendaEntity.getSubject())
                .build();
    }

    public static AgendaListResponse mapToAgendaList(List<AgendaEntity> agendaEntityList) {
        List<AgendaResponseModel> response = agendaEntityList.parallelStream()
                .map(agenda -> AgendaResponseModel.builder()
                        .id(agenda.getId())
                        .subject(agenda.getSubject())
                        .build()).collect(Collectors.toList());
        return AgendaListResponse.builder()
                .list(response)
                .quantity(response.size())
                .build();
    }
}
