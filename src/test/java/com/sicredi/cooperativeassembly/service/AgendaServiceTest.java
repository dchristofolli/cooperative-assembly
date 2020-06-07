package com.sicredi.cooperativeassembly.service;

import com.sicredi.cooperativeassembly.Stub;
import com.sicredi.cooperativeassembly.data.repository.AgendaRepository;
import com.sicredi.cooperativeassembly.exception.ApiException;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static com.sicredi.cooperativeassembly.Stub.agendaEntityStub;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AgendaServiceTest {
    @Mock
    AgendaRepository agendaRepository;
    @InjectMocks
    AgendaService agendaService;

    @Test
    public void save() {
        when(agendaRepository.save(agendaEntityStub())).thenReturn(agendaEntityStub());
        agendaService.save(Stub.agendaRequestStub());
        assertEquals(agendaEntityStub(), agendaRepository.save(agendaEntityStub()));
    }

    @Test
    public void existsById() {
        when(agendaRepository.existsById("123456")).thenReturn(true);
        agendaService.existsById("123456");
        assertTrue(agendaRepository.existsById("123456"));
    }

    @Test
    public void findAll_ok() {
        when(agendaRepository.findAll()).thenReturn(Collections.singletonList(agendaEntityStub()));
        agendaService.findAll();
        assertEquals(Collections.singletonList(agendaEntityStub()), agendaRepository.findAll());
    }

    @Test(expected = ApiException.class)
    public void findAll_notFound() {
        when(agendaRepository.findAll()).thenReturn(Collections.emptyList());
        agendaService.findAll();
        assertEquals(Collections.singletonList(agendaEntityStub()), agendaRepository.findAll());
    }
}