package com.example.paymentservice.kafka.producer.dto;

import com.example.paymentservice.kafka.producer.enums.EventType;
import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReturnPaymentEventDTO {
     Long orderId;

    float amount;

    EventType eventType = EventType.OP_SINGLE;
}
