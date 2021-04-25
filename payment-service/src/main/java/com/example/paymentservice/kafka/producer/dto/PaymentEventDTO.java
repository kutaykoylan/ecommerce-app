package com.example.paymentservice.kafka.producer.dto;

import com.example.paymentservice.kafka.producer.enums.EventType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentEventDTO {
    private float amount;

    private Long orderId;

    private String paymentAddress;

    private String cardInformation;

    private EventType eventType;
}
