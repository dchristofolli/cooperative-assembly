package com.sicredi.cooperativeassembly;

import com.sicredi.cooperativeassembly.data.entity.AgendaEntity;
import com.sicredi.cooperativeassembly.data.entity.SessionEntity;
import com.sicredi.cooperativeassembly.model.agenda.AgendaRequest;
import com.sicredi.cooperativeassembly.model.session.SessionRequest;

import java.time.Instant;
import java.util.Collections;

public class Stub {
    public static AgendaRequest agendaStub(){
        return AgendaRequest.builder()
                .subject("Assunto")
                .description("Descrição")
                .build();
    }

    public static AgendaEntity agendaEntityStub(){
        return AgendaEntity.builder()
                .id("123456")
                .subject("Assunto")
                .description("Descrição")
                .build();
    }

    public static AgendaRequest agendaRequestStub() {
        return AgendaRequest.builder()
                .subject("Assunto")
                .description("Descrição")
                .build();
    }

    public static SessionEntity sessionEntityStub(){
        return SessionEntity.builder()
                .sessionId("123456")
                .agendaId("123")
                .cpfAlreadyVoted(Collections.singletonList("05553232694"))
                .votes(Collections.singletonList("S"))
                .isActive(true)
                .build();
    }

    public static SessionRequest sessionRequestStub(){
        return SessionRequest.builder()
                .agendaId("123")
                .build();
    }
}
