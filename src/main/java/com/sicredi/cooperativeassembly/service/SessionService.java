package com.sicredi.cooperativeassembly.service;

import com.sicredi.cooperativeassembly.AgendaRepository;
import com.sicredi.cooperativeassembly.entity.AgendaEntity;
import com.sicredi.cooperativeassembly.exception.ApiException;
import com.sicredi.cooperativeassembly.model.AgendaRegistrationModel;
import com.sicredi.cooperativeassembly.model.ResultModel;
import com.sicredi.cooperativeassembly.model.VotingModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.sicredi.cooperativeassembly.mapper.AgendaMapper.mapAgendaToEntity;

@Service
@AllArgsConstructor
@EnableScheduling
@Slf4j
public class SessionService {
    private final AgendaRepository agendaRepository;

    public AgendaEntity createAgenda(AgendaRegistrationModel agendaRegistrationModel) {
        return agendaRepository.save(mapAgendaToEntity(agendaRegistrationModel));
    }

    public AgendaEntity findById(String agendaId) {
        return agendaRepository.findById(agendaId)
                .orElseThrow(() -> new ApiException("Agenda not found", HttpStatus.NOT_FOUND));
    }

    public void openVotingSession(String agendaId, Long timeInSeconds) {
        long timeInMinute=60L;
        if(timeInSeconds != null)
            timeInMinute = timeInSeconds * 60;
        AgendaEntity agendaEntity = findById(agendaId);
        agendaEntity.setSessionCloseTime(Instant.now().plusSeconds(timeInMinute));
        agendaRepository.save(agendaEntity);
    }

    public Boolean sessionIsOpen(String agendaId) {
        return findById(agendaId).getSessionCloseTime().isAfter(Instant.now());
    }

    public List<AgendaEntity> findAllOpenSessions() {
        List<AgendaEntity> activeSessions = agendaRepository.findAll().parallelStream()
                .filter(a -> a.getSessionCloseTime().isAfter(Instant.now()))
                .collect(Collectors.toList());
        if (activeSessions.isEmpty()) {
            throw new ApiException("There are no active sessions", HttpStatus.NOT_FOUND);
        }
        return activeSessions;
    }

    public void vote(VotingModel votingModel) {
        AgendaEntity agendaEntity = findById(votingModel.getAgendaId());
        List<String> votes = agendaEntity.getVotes();
        List<String> cpf = agendaEntity.getCpfAlreadyVoted();
        votes.add(votingModel.getOption().toUpperCase());
        cpf.add(votingModel.getCpf());

        agendaEntity.setVotes(votes);
        agendaEntity.setCpfAlreadyVoted(cpf);
        agendaRepository.save(agendaEntity);
    }

    public Boolean cpfAlreadyVotedOnThisSession(VotingModel votingModel) {
        return findById(votingModel.getAgendaId()).getCpfAlreadyVoted().contains(votingModel.getCpf());
    }

    public ResultModel votationResult(String id) {
        List<String> votes = findById(id).getVotes();
        return ResultModel.builder()
                .agendaId(id)
                .favor(votes.parallelStream().filter(v -> v.equals("S")).count())
                .against(votes.parallelStream().filter(v -> v.equals("N")).count())
                .total(votes.size())
                .build();
    }
}
