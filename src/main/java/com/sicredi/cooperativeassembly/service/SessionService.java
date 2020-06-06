package com.sicredi.cooperativeassembly.service;

import com.sicredi.cooperativeassembly.AgendaRepository;
import com.sicredi.cooperativeassembly.entity.AgendaEntity;
import com.sicredi.cooperativeassembly.exception.ApiException;
import com.sicredi.cooperativeassembly.model.AgendaRegistrationModel;
import com.sicredi.cooperativeassembly.model.AgendaResponseModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.sicredi.cooperativeassembly.mapper.AgendaMapper.mapAgendaToEntity;
import static com.sicredi.cooperativeassembly.mapper.AgendaMapper.mapEntityToResponse;

@Service
@AllArgsConstructor
@EnableScheduling
@Slf4j
public class SessionService {
    private AgendaRepository agendaRepository;

    public AgendaResponseModel createAgenda(AgendaRegistrationModel agendaRegistrationModel) {
        return mapEntityToResponse(agendaRepository.save(mapAgendaToEntity(agendaRegistrationModel)));
    }

    public void openVotingSession(String agendaId, Long timeInSeconds) {
        AgendaEntity agendaEntity = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new ApiException("Agenda not found", HttpStatus.NOT_FOUND));
        agendaEntity.setSessionCloseTime(Instant.now().plusSeconds(timeInSeconds * 60));
        agendaRepository.save(agendaEntity);
    }

//    private Boolean sessionManagement(String agendaId) {
//        AgendaEntity agendaEntity = agendaRepository.findById(agendaId)
//                .orElseThrow(() -> new ApiException("Agenda not found", HttpStatus.NOT_FOUND));
//        return agendaEntity.getSessionCloseTime() > Calendar.getInstance().getTimeInMillis();
//    }

    public List<AgendaEntity> findAllOpenSessions() {
        List<AgendaEntity> activeSessions = agendaRepository.findAll().parallelStream()
                .filter(a -> a.getSessionCloseTime().isAfter(Instant.now()))
                .collect(Collectors.toList());
        if (activeSessions.isEmpty()) {
            throw new ApiException("There are no active sessions", HttpStatus.NOT_FOUND);
        }
        return activeSessions;
    }
}
