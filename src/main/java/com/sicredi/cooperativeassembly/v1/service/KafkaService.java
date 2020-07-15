package com.sicredi.cooperativeassembly.v1.service;

import com.sicredi.cooperativeassembly.kafka.EventProducer;
import com.sicredi.cooperativeassembly.v1.dto.session.SessionResult;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaService {
    private final EventProducer eventProducer;
    @Value("${session-result.kafka.topic}")
    private final String topic;

    public void send(ProducerRecord<String, Object> producerRecord) {
        eventProducer.send(producerRecord);
    }

    public ProducerRecord<String, Object> makeRecord(SessionResult sessionResult) {
        return new ProducerRecord<>(topic, sessionResult.getSessionId(),
                sessionResult);
    }
}
