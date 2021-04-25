package com.example.paymentservice.kafka.consumer;

import com.example.paymentservice.common.exception.PaymentException;
import com.example.paymentservice.kafka.consumer.dto.ProcessPaymentDTO;
import com.example.paymentservice.kafka.producer.PaymentProducerService;
import com.example.paymentservice.kafka.producer.dto.PaymentEventDTO;
import com.example.paymentservice.kafka.producer.enums.EventType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PaymentConsumerService {
    private final PaymentProducerService paymentProducerService;

    @KafkaListener(topics = "process-payment", groupId = "payment-service")
    public void processPayment(String message) throws JsonProcessingException, PaymentException {
        ProcessPaymentDTO processPaymentDTO = getProcessPaymentDTO(message);
        PaymentEventDTO successfulPaymentEventDTO = PaymentEventDTO.builder()
                .paymentAddress(processPaymentDTO.getPaymentInformation().getPaymentAddress())
                .orderId(processPaymentDTO.getOrderId())
                .build();
        if(processPaymentDTO.getPaymentInformation().getAmount()>2000)
            throw new PaymentException("Payment amount cannot be more than 2000");
        if(processPaymentDTO.getPaymentInformation().getAmount()>1000){
            PaymentEventDTO failedPaymentEventDTO = PaymentEventDTO.builder()
                    .orderId(processPaymentDTO.getOrderId())
                    .paymentAddress(processPaymentDTO.getPaymentInformation().getPaymentAddress())
                    .build();
            paymentProducerService.sendFailPaymentEvent(failedPaymentEventDTO);
        }
        paymentProducerService.sendSucessPaymentEvent(successfulPaymentEventDTO);
    }

    private ProcessPaymentDTO getProcessPaymentDTO(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(message, ProcessPaymentDTO.class);
    }
}