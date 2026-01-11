package com.org.notification.consumer;

import com.org.notification.dto.EmpruntEventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmpruntEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EmpruntEventConsumer.class);

    @KafkaListener(topics = "emprunt-created", groupId = "notification-group")
    public void consumeEmpruntCreatedEvent(EmpruntEventDTO event) {
        logger.info("==========================================");
        logger.info("NOTIFICATION: Nouvel emprunt créé");
        logger.info("==========================================");
        logger.info("Emprunt ID: {}", event.getEmpruntId());
        logger.info("User ID: {}", event.getUserId());
        logger.info("Book ID: {}", event.getBookId());
        logger.info("Event Type: {}", event.getEventType());
        logger.info("Timestamp: {}", event.getTimestamp());
        logger.info("==========================================");
        
        // Simulation de notification (affichage console)
        System.out.println("\n>>> NOTIFICATION RECEIVED <<<");
        System.out.println("Un nouvel emprunt a été créé:");
        System.out.println("  - Emprunt ID: " + event.getEmpruntId());
        System.out.println("  - User ID: " + event.getUserId());
        System.out.println("  - Book ID: " + event.getBookId());
        System.out.println("  - Date: " + event.getTimestamp());
        System.out.println(">>> END NOTIFICATION <<<\n");
    }
}
