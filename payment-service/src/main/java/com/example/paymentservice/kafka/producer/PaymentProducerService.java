package com.example.paymentservice.kafka.producer;

import com.example.paymentservice.kafka.producer.dto.PaymentEventDTO;
import com.example.paymentservice.kafka.producer.dto.ReturnPaymentEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PaymentProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendSucessPaymentEvent(PaymentEventDTO paymentEventDTO) {
        kafkaTemplate.send("success-payment", paymentEventDTO);
    }

    public void sendFailPaymentEvent(PaymentEventDTO paymentEventDTO) {
        kafkaTemplate.send("fail-payment", paymentEventDTO);
    }

    public void sendReturnPaymentEvent(ReturnPaymentEventDTO returnPaymentEventDTO){
        kafkaTemplate.send("return-payment", returnPaymentEventDTO);
    }
}
