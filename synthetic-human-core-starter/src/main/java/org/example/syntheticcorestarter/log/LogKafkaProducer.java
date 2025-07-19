package org.example.syntheticcorestarter.log;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topic.name}")
    private String topicName;

    public void sendLog(String message) {
        try {
            kafkaTemplate.send(topicName, message);
            log.info("Sent to Kafka: {}", message);
        } catch (Exception e) {
            log.error("Failed to send log to Kafka", e);
        }
    }
}
