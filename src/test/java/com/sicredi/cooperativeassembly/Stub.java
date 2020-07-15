package com.sicredi.cooperativeassembly;

import com.sicredi.cooperativeassembly.domain.model.AgendaEntity;
import com.sicredi.cooperativeassembly.domain.model.SessionEntity;
import com.sicredi.cooperativeassembly.v1.dto.agenda.AgendaListResponse;
import com.sicredi.cooperativeassembly.v1.dto.agenda.AgendaRequest;
import com.sicredi.cooperativeassembly.v1.dto.agenda.AgendaResponse;
import com.sicredi.cooperativeassembly.v1.dto.session.SessionRequest;
import com.sicredi.cooperativeassembly.v1.dto.session.SessionResponse;

import java.util.Collections;

public class Stub {

    public static AgendaListResponse agendaListResponseStub(){
        return AgendaListResponse.builder()
                .list(Collections.singletonList(agendaResponseStub()))
                .quantity(1)
                .build();
    };

    public static AgendaResponse agendaResponseStub(){
        return AgendaResponse.builder()
                .id("123456")
                .subject("Assunto")
                .build();
    }

    public static AgendaEntity agendaEntityStub() {
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

    public static SessionRequest sessionRequestStub() {
        return SessionRequest.builder()
                .agendaId("1")
                .minutesRemaining(10L)
                .build();
    }

    public static SessionEntity sessionEntityStub() {
        return SessionEntity.builder()
                .sessionId("123456")
                .agendaId("123")
                .cpfAlreadyVoted(Collections.singletonList("01063682061"))
                .votes(Collections.singletonList("S"))
                .build();
    }

    public static SessionResponse sessionResponseStub(){
        return SessionResponse.builder()
                .sessionId("123456")
                .build();
    }
}
