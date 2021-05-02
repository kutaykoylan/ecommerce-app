package com.example.orderservice.kafka.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnPaymentEventDTO {
    private Long orderId;

    private float amount;
}

