package com.sicredi.cooperativeassembly.kafka;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@lombok.AllArgsConstructor
@Service
public class EventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Gson gson;
    @Value("${session-result.kafka.topic}")
    private final String topic;

    public void send(ProducerRecord<String, Object> producerRecord) {
        kafkaTemplate.send(topic, gson.toJson(producerRecord));
    }
}
