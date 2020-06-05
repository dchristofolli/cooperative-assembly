package com.sicredi.cooperativeassembly.service;

import com.sicredi.cooperativeassembly.AgendaRepository;
import com.sicredi.cooperativeassembly.model.AgendaRegistrationModel;
import com.sicredi.cooperativeassembly.model.AgendaResponseModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sicredi.cooperativeassembly.mapper.AgendaMapper.mapAgendaToEntity;
import static com.sicredi.cooperativeassembly.mapper.AgendaMapper.mapEntityToResponse;

@Service
@AllArgsConstructor
public class SessionService {
    private AgendaRepository agendaRepository;

    public AgendaResponseModel createAgenda(AgendaRegistrationModel agendaRegistrationModel) {
        return mapEntityToResponse(agendaRepository.save(mapAgendaToEntity(agendaRegistrationModel)));
    }
}
