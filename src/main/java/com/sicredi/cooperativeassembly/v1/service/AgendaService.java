package com.sicredi.cooperativeassembly.v1.service;

import com.sicredi.cooperativeassembly.domain.model.AgendaEntity;
import com.sicredi.cooperativeassembly.exception.ApiException;
import com.sicredi.cooperativeassembly.v1.dto.agenda.AgendaRequest;
import com.sicredi.cooperativeassembly.domain.repository.AgendaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sicredi.cooperativeassembly.v1.mapper.AgendaMapper.mapAgendaToEntity;

@Service
@AllArgsConstructor
public class AgendaService {
    private final AgendaRepository agendaRepository;

    public AgendaEntity save(AgendaRequest agendaRequest) {
        return agendaRepository.save(mapAgendaToEntity(agendaRequest));
    }

    public boolean existsById(String agendaId) {
        return agendaRepository.existsById(agendaId);
    }

    public List<AgendaEntity> findAll() {
        List<AgendaEntity> agendaEntities = agendaRepository.findAll();
        if (agendaEntities.isEmpty())
            throw new ApiException("There are no agendas", HttpStatus.NOT_FOUND);
        return agendaEntities;
    }

    public AgendaEntity findById(String agendaId) {
        return agendaRepository.findById(agendaId)
                .orElseThrow(() -> new ApiException("Agenda not found", HttpStatus.NOT_FOUND));
    }
}
