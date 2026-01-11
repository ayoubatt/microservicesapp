package com.org.emprunt.kafka;

import com.org.emprunt.DTO.EmpruntEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmpruntEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(EmpruntEventProducer.class);
    private static final String TOPIC = "emprunt-created";

    private final KafkaTemplate<String, EmpruntEventDTO> kafkaTemplate;

    public EmpruntEventProducer(KafkaTemplate<String, EmpruntEventDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmpruntCreatedEvent(EmpruntEventDTO event) {
        logger.info("Publishing emprunt created event: {}", event);
        kafkaTemplate.send(TOPIC, event);
        logger.info("Event published successfully to topic: {}", TOPIC);
    }
}
