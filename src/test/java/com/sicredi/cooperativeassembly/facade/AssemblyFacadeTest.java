package com.sicredi.cooperativeassembly.facade;

import com.sicredi.cooperativeassembly.Stub;
import com.sicredi.cooperativeassembly.data.entity.SessionEntity;
import com.sicredi.cooperativeassembly.exception.ApiException;
import com.sicredi.cooperativeassembly.model.session.SessionListResponse;
import com.sicredi.cooperativeassembly.model.session.SessionRequest;
import com.sicredi.cooperativeassembly.model.session.SessionResponse;
import com.sicredi.cooperativeassembly.model.session.SessionResult;
import com.sicredi.cooperativeassembly.model.vote.VoteModel;
import com.sicredi.cooperativeassembly.service.AgendaService;
import com.sicredi.cooperativeassembly.service.SessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static com.sicredi.cooperativeassembly.Stub.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AssemblyFacadeTest {
    @Mock
    AgendaService agendaService;
    @Mock
    SessionService sessionService;
    @InjectMocks
    AssemblyFacade assemblyFacade;

    @Test
    public void createAgenda() {
        when(agendaService.save(agendaRequestStub())).thenReturn(agendaEntityStub());
        assemblyFacade.createAgenda(agendaRequestStub());
        assertEquals(agendaEntityStub(), agendaService.save(agendaRequestStub()));
    }

//    @Test
//    public void createVotingSession() {
//        when(agendaService.existsById("1")).thenReturn(true);
//        when(sessionService.createVotingSession(sessionEntityStub())).thenReturn(sessionEntityStub());
//        assemblyFacade.createVotingSession(sessionRequestStub());
//        assertEquals(sessionEntityStub(), sessionService.createVotingSession(sessionEntityStub()));
//    }

    @Test
    public void findAllOpenSessions() {
        when(sessionService.findAllOpenSessions()).thenReturn(Collections.singletonList(SessionEntity.builder()
                .sessionId("1")
                .build()));
        assemblyFacade.findAllOpenSessions();
        assertEquals(SessionListResponse.builder()
                .list(Collections.singletonList(SessionResponse.builder()
                        .sessionId("1")
                        .build()))
                .quantity(1)
                .build(), assemblyFacade.findAllOpenSessions());
    }

//    @Test
//    public void vote_ok() {
//        VoteModel voteModel = VoteModel.builder()
//                .sessionId("1")
//                .cpf("05553232694")
//                .option("S")
//                .build();
//        when(sessionService.sessionIsActive("1")).thenReturn(true);
//        when(sessionService.alreadyVotedOnThisSession(voteModel)).thenReturn(false);
//        when(sessionService.sessionIsActive("1")).thenReturn(true);
//        assemblyFacade.vote(voteModel);
//        assertEquals(voteModel, assemblyFacade.vote(voteModel));
//    }

    @Test(expected = ApiException.class)
    public void vote_sessionIsNotActive() {
        VoteModel voteModel = VoteModel.builder()
                .sessionId("1")
                .cpf("05553232694")
                .option("S")
                .build();
        when(sessionService.sessionIsActive("1")).thenReturn(false);
        when(sessionService.alreadyVotedOnThisSession(voteModel)).thenReturn(true);
        assemblyFacade.vote(voteModel);
        assertNull(assemblyFacade.vote(voteModel));
    }

    @Test(expected = ApiException.class)
    public void vote_userAlreadyVoted() {
        VoteModel voteModel = VoteModel.builder()
                .sessionId("1")
                .cpf("05553232694")
                .option("S")
                .build();
        when(sessionService.sessionIsActive("1")).thenReturn(true);
        when(sessionService.alreadyVotedOnThisSession(voteModel)).thenReturn(true);
        assemblyFacade.vote(voteModel);
        assertNull(assemblyFacade.vote(voteModel));
    }

//    @Test
//    public void sessionResult() {
//        SessionResult result = SessionResult.builder()
//                .total(1)
//                .favor(1L)
//                .build();
//        when(sessionService.sessionIsActive("1")).thenReturn(true);
//        when(sessionService.votationResult("1")).thenReturn(result);
//        assemblyFacade.sessionResult("1");
//        assertEquals(result, assemblyFacade.sessionResult("1"));
//    }

    @Test
    public void findAllAgendas() {
    }
}