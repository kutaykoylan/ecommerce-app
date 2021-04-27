package com.example.orderservice.kafka.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReturnPaymentEventDTO {
    Long orderId;

    float amount;
}

