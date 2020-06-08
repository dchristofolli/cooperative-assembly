//package com.sicredi.cooperativeassembly;
//
//import com.sicredi.cooperativeassembly.v1.controller.AssemblyController;
//import com.sicredi.cooperativeassembly.data.repository.AgendaRepository;
//import com.sicredi.cooperativeassembly.data.repository.SessionRepository;
//import com.sicredi.cooperativeassembly.v1.facade.AssemblyFacade;
//import com.sicredi.cooperativeassembly.service.AgendaService;
//import com.sicredi.cooperativeassembly.service.SessionService;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = AssemblyController.class)
//@ContextConfiguration(classes = {
//        AssemblyFacade.class,
//        AgendaService.class,
//        SessionService.class
//})
//@AutoConfigureMockMvc
//public class AssemblyControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private AgendaRepository agendaRepository;
//    @MockBean
//    private SessionRepository sessionRepository;
//    @InjectMocks
//    private AssemblyController assemblyController;
//
//}