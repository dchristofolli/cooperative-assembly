package com.sicredi.cooperativeassembly.service;

import com.sicredi.cooperativeassembly.data.entity.SessionEntity;
import com.sicredi.cooperativeassembly.data.repository.SessionRepository;
import com.sicredi.cooperativeassembly.exception.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static com.sicredi.cooperativeassembly.Stub.sessionEntityStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class SessionServiceTest {
    @Mock
    SessionRepository sessionRepository;
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
        given(sessionRepository.findById("123")).willReturn(Optional.of(sessionEntityStub()));
        sessionService.sessionIsActive("123");
        assertTrue(sessionService.sessionIsActive("123"));
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
}