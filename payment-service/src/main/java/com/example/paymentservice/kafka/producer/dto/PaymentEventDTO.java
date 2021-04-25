package com.example.paymentservice.kafka.producer.dto;

import com.example.paymentservice.kafka.producer.enums.EventType;
import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentEventDTO {
     float amount;

     Long orderId;

     String paymentAddress;

     String cardInformation;
}
