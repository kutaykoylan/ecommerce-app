package com.example.paymentservice.event.consumer;

import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumerService {

    @KafkaListener(topics = "process-payment", groupId = "payment-service")
    public void processPayment(String message) {
        System.out.println("Received Message in group payment service: " + message);
    }
}
