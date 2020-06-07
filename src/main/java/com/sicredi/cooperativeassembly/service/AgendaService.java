package com.sicredi.cooperativeassembly.service;

import com.sicredi.cooperativeassembly.data.entity.AgendaEntity;
import com.sicredi.cooperativeassembly.exception.ApiException;
import com.sicredi.cooperativeassembly.model.agenda.AgendaRequest;
import com.sicredi.cooperativeassembly.data.repository.AgendaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sicredi.cooperativeassembly.mapper.AgendaMapper.mapAgendaToEntity;

@Service
@AllArgsConstructor
public class AgendaService {
    private final AgendaRepository agendaRepository;

    public AgendaEntity save(AgendaRequest agendaRequest) {
        return agendaRepository.save(mapAgendaToEntity(agendaRequest));
    }

    public Boolean existsById(String agendaId) {
        return agendaRepository.existsById(agendaId);
    }

    public List<AgendaEntity> findAll() {
        List<AgendaEntity> agendaEntities = agendaRepository.findAll();
        if (agendaEntities.isEmpty())
            throw new ApiException("There are no agendas found", HttpStatus.NOT_FOUND);
        return agendaEntities;
    }

    public AgendaEntity findById(String agendaId) {
        return agendaRepository.findById(agendaId)
                .orElseThrow(() -> new ApiException("Agenda not found", HttpStatus.NOT_FOUND));
    }
}
