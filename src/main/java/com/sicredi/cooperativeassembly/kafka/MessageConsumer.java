package com.sicredi.cooperativeassembly.kafka;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Data
public class MessageConsumer {
    private final CountDownLatch countDownLatch= new CountDownLatch(1);

    @KafkaListener(topics = "${session-result.kafka.topic}")
    public void consumer(String payload){
        log.info("Received voting session result: {}", payload);
        countDownLatch.countDown();
    }
}
