package com.example.orderservice.kafka.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnPaymentEventDTO {
    @NotNull
    private Long orderId;
    @NotNull
    private float amount;
}

