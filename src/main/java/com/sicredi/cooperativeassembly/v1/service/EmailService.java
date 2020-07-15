package com.sicredi.cooperativeassembly.v1.service;

import com.sicredi.cooperativeassembly.domain.model.EmailEntity;
import com.sicredi.cooperativeassembly.domain.repository.EmailRepository;
import com.sicredi.cooperativeassembly.v1.dto.session.SessionResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {
    EmailRepository emailRepository;
    private final JavaMailSender sender;

    @Value("${mail.from}")
    private final String from;

    private List<EmailEntity> findAllEmails() {
        return emailRepository.findAll();
    }

    public void mailSend(SessionResult result) {
        SimpleMailMessage message = new SimpleMailMessage();
        try{
            findAllEmails().parallelStream()
                    .forEach(email -> {
                        message.setSubject("Resultados da Sessão " + result.getSessionId());
                        message.setFrom(from);
                        message.setTo(email.getEmail());
                        message.setText(String.valueOf(result));
                        sender.send(message);
                    });
        }catch (Exception e){
            log.warn(e.getMessage());
        }
    }
}
