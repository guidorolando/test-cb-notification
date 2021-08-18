package com.test.cbnotification.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.cbnotification.kafka.message.BetRequest;
import com.test.cbnotification.notification.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Consumer {
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private EmailSenderService emailSenderService;

    @KafkaListener(topics = "notification", groupId = "group_id")
    public void consume(String message) throws IOException {
        logger.info(String.format("### -> Consume notification -> %s", message));
        BetRequest betRequest = this.convertToBetRequest(message);
        logger.info(String.format("### -> %s", betRequest.getEmail()));
        this.sendNotification(betRequest);
    }

    private BetRequest convertToBetRequest(String betRequest) {
        ObjectMapper objectMapper = new ObjectMapper();

        BetRequest betRequestResult = null;
        try {
            betRequestResult = objectMapper.readValue(betRequest, BetRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.info("Converted betRequest fail");
        }
        return betRequestResult;
    }

    private void sendNotification(BetRequest betRequest) {
        Double value = Math.abs(betRequest.getNomicsValue() - betRequest.getBetValue());
        String message = "You win the game with Value difference: " + value;
        String subject = "CRYPTO BEST win notification";
        this.emailSenderService.sendSimpleMail(betRequest.getEmail(), message, subject);
    }
}
