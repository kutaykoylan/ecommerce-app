package com.example.paymentservice.kafka.producer.dto;

import com.example.paymentservice.kafka.producer.enums.EventType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReturnPaymentEventDTO {
    private Long orderId;

    private float amount;

    private EventType eventType = EventType.OP_SINGLE;
}
