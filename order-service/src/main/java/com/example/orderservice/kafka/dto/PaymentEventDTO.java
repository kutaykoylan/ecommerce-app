package com.example.orderservice.kafka.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class PaymentEventDTO {
    float amount;

    Long orderId;

    String paymentAddress;

    String cardInformation;
}
