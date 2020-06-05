package com.sicredi.cooperativeassembly.mapper;

import com.sicredi.cooperativeassembly.entity.AgendaEntity;
import com.sicredi.cooperativeassembly.model.AgendaRegistrationModel;
import com.sicredi.cooperativeassembly.model.AgendaResponseModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AgendaMapper {
    public static AgendaEntity mapAgendaToEntity(AgendaRegistrationModel agendaRegistrationModel){
        return AgendaEntity.builder()
                .subject(agendaRegistrationModel.getSubject())
                .description(agendaRegistrationModel.getDescription())
                .build();
    }

    public static AgendaResponseModel mapEntityToResponse(AgendaEntity agendaEntity){
        return AgendaResponseModel.builder()
                .id(agendaEntity.getId())
                .subject(agendaEntity.getSubject())
                .build();
    }
}
