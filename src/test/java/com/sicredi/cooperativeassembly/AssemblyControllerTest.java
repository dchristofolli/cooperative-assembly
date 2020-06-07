package com.sicredi.cooperativeassembly;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.cooperativeassembly.controller.AssemblyController;
import com.sicredi.cooperativeassembly.data.repository.AgendaRepository;
import com.sicredi.cooperativeassembly.data.repository.SessionRepository;
import com.sicredi.cooperativeassembly.facade.AssemblyFacade;
import com.sicredi.cooperativeassembly.service.AgendaService;
import com.sicredi.cooperativeassembly.service.SessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.sicredi.cooperativeassembly.Stub.agendaEntityStub;
import static com.sicredi.cooperativeassembly.Stub.agendaRequestStub;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AssemblyController.class)
@ContextConfiguration(classes = {
        AssemblyFacade.class,
        AgendaService.class,
        SessionService.class
})
@AutoConfigureMockMvc
public class AssemblyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AgendaRepository agendaRepository;
    @MockBean
    private SessionRepository sessionRepository;
    @InjectMocks
    private AssemblyController assemblyController;

    @Test
    public void createAgenda() throws Exception {
        assemblyController.createAgenda(agendaRequestStub());
        mockMvc.perform(post("/assembly/agenda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(agendaEntityStub())))
                .andExpect(status().isCreated());
    }

    @Test
    public void findAllAgendas() {
    }

    @Test
    public void createSession() {
    }

    @Test
    public void findAllOpenSessions() {
    }

    @Test
    public void vote() {
    }

    @Test
    public void sessionResult() {
    }
}