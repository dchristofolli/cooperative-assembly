package com.sicredi.cooperativeassembly.service;

import com.sicredi.cooperativeassembly.data.entity.EmailEntity;
import com.sicredi.cooperativeassembly.data.entity.SessionEntity;
import com.sicredi.cooperativeassembly.data.repository.EmailRepository;
import com.sicredi.cooperativeassembly.data.repository.SessionRepository;
import com.sicredi.cooperativeassembly.exception.ApiException;
import com.sicredi.cooperativeassembly.v1.model.session.SessionResult;
import com.sicredi.cooperativeassembly.v1.model.vote.VoteModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sicredi.cooperativeassembly.Stub.sessionEntityStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class SessionServiceTest {
    @Mock
    SessionRepository sessionRepository;
    @Mock
    EmailRepository emailRepository;
    @InjectMocks
    SessionService sessionService;

    @Test
    public void findSessionById() {
        when(sessionRepository.findById("123456")).thenReturn(Optional.of(sessionEntityStub()));
        sessionService.findSessionById("123456");
        assertEquals(sessionEntityStub(), sessionService.findSessionById("123456"));
    }

    @Test
    public void createVotingSession() {
        when(sessionRepository.save(sessionEntityStub())).thenReturn(sessionEntityStub());
        sessionService.createVotingSession(sessionEntityStub());
        assertEquals(sessionEntityStub(), sessionService.createVotingSession(sessionEntityStub()));
    }

    @Test
    public void sessionIsActive() {
        SessionEntity sessionEntity = sessionEntityStub();
        sessionEntity.setSessionCloseTime(Instant.now().plusSeconds(10));
        given(sessionRepository.save(sessionEntity)).willReturn(sessionEntity);
        given(sessionRepository.findById("123456")).willReturn(Optional.of(sessionEntity));
        sessionService.createVotingSession(sessionEntity);
        assertTrue(sessionService.sessionIsActive("123456"));
    }

    @Test
    public void findAllOpenSessions_ok() {
        SessionEntity sessionEntity = SessionEntity.builder().sessionCloseTime(Instant.now().plusSeconds(60)).build();
        given(sessionRepository.findAll()).willReturn(Collections.singletonList(sessionEntity));
        sessionService.findAllOpenSessions();
        assertEquals(Collections.singletonList(sessionEntity), sessionService.findAllOpenSessions());
    }

    @Test(expected = ApiException.class)
    public void findAllOpenSessions_NotFound() {
        SessionEntity sessionEntity = SessionEntity.builder().sessionCloseTime(Instant.now().minusSeconds(60)).build();
        given(sessionRepository.findAll()).willReturn(Collections.singletonList(sessionEntity));
        sessionService.findAllOpenSessions();
        assertEquals(Collections.singletonList(sessionEntity), sessionService.findAllOpenSessions());
    }

    @Test
    public void findAllClosedSessions_ok() {
        SessionEntity sessionEntity = SessionEntity.builder().sessionCloseTime(Instant.now().minusSeconds(60)).build();
        given(sessionRepository.findAll()).willReturn(Collections.singletonList(sessionEntity));
        sessionService.findAllClosedSessions();
        assertEquals(Collections.singletonList(sessionEntity), sessionService.findAllClosedSessions());
    }

    @Test
    public void findAllClosedSessions_notFound() {
        SessionEntity sessionEntity = SessionEntity.builder().sessionCloseTime(Instant.now().plusSeconds(60)).build();
        given(sessionRepository.findAll()).willReturn(Collections.singletonList(sessionEntity));
        sessionService.findAllClosedSessions();
        assertEquals(Collections.emptyList(), sessionService.findAllClosedSessions());
    }

    @Test
    public void vote() {
        List<String> votesList = new ArrayList<>();
        votesList.add("S");
        List<String> cpfList = new ArrayList<>();
        cpfList.add("85337392069");
        EmailEntity emailEntity = EmailEntity.builder()
                .id("123")
                .email("edward.nygma@gotham.com")
                .build();
        SessionEntity session = SessionEntity.builder()
                .sessionId("123456")
                .agendaId("1")
                .sessionCloseTime(Instant.now().plusSeconds(60))
                .votes(votesList)
                .cpfAlreadyVoted(cpfList)
                .build();

        VoteModel vote = VoteModel.builder()
                .sessionId("123456")
                .email("edward.nygma@gotham.com")
                .cpf("93011201005")
                .option("N")
                .build();

        cpfList.add(vote.getCpf());
        votesList.add(vote.getOption());
        session.setCpfAlreadyVoted(cpfList);
        session.setVotes(votesList);
        when(sessionRepository.findById("123456")).thenReturn(Optional.of(session));
        when(emailRepository.save(emailEntity)).thenReturn(emailEntity);
        sessionService.vote(vote);
        assertEquals(vote, sessionService.vote(vote));
    }

    @Test
    public void alreadyVotedOnThisSession() {
        SessionEntity session = sessionEntityStub();
        VoteModel vote = VoteModel.builder()
                .sessionId("123456")
                .cpf("01063682061")
                .option("N")
                .build();
        when(sessionRepository.findById("123456")).thenReturn(Optional.of(session));
        sessionRepository.findById("123456");
        assertEquals(true, sessionService.alreadyVotedOnThisSession(vote));
    }

    @Test
    public void checkSessionResult() {
        SessionEntity session = sessionEntityStub();
        SessionResult result = SessionResult.builder()
                .sessionId("123456")
                .agendaId("123")
                .favor(1L)
                .against(0L)
                .total(1)
                .build();
        when(sessionRepository.findById("123456")).thenReturn(Optional.of(session));
        sessionService.checkSessionResult("123456");
        assertEquals(result, sessionService.checkSessionResult("123456"));
    }

    @Test
    public void getSessionResults_ok() {
        SessionEntity session = sessionEntityStub();
        session.setSessionCloseTime(Instant.now().minusSeconds(60));
        SessionResult result = SessionResult.builder()
                .sessionId("123456")
                .agendaId("123")
                .favor(1L)
                .against(0L)
                .total(1)
                .build();
        List<SessionResult> resultList = Collections.singletonList(result);
        when(sessionRepository.findAll().parallelStream()
                .filter(a -> a.getSessionCloseTime().isBefore(Instant.now()))
                .collect(Collectors.toList())).thenReturn(Collections.singletonList(session));
        when(sessionRepository.findById("123456")).thenReturn(Optional.of(session));
        List<SessionResult> results = sessionService.getSessionResults();
        assertEquals(resultList, results);
    }

    @Test
    public void setMessageAlreadySent() {
        SessionEntity session = sessionEntityStub();
        session.setMessageAlreadySent("S");
        when(sessionRepository.save(session)).thenReturn(session);
        sessionService.setMessageAlreadySent(session);
        verify(sessionRepository).save(session);
    }
}