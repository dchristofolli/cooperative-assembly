package com.sicredi.cooperativeassembly.v1.service;

import com.sicredi.cooperativeassembly.domain.model.EmailEntity;
import com.sicredi.cooperativeassembly.domain.repository.EmailRepository;
import com.sicredi.cooperativeassembly.v1.dto.session.SessionResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EmailServiceTest {
    @Mock
    EmailRepository emailRepository;
    @InjectMocks
    EmailService emailService;

    @Test
    public void mailSend() {
        SimpleMailMessage message = new SimpleMailMessage();
        SessionResult result = SessionResult.builder()
                .sessionId("123456")
                .agendaId("123")
                .favor(1L)
                .against(0L)
                .total(1)
                .build();
        EmailEntity emailEntity = EmailEntity.builder()
                .id("123")
                .email("edward.nygma@gotham.com")
                .build();
        List<EmailEntity> emailList = Collections.singletonList(emailEntity);
        message.setSubject("Resultados da Sessão " + result.getSessionId());
        message.setFrom("email@sent.from");
        message.setTo(emailEntity.getEmail());
        message.setText(String.valueOf(result));
        when(emailRepository.findAll()).thenReturn(emailList);
        emailService.mailSend(result);
        assertNotNull(message);
    }
}