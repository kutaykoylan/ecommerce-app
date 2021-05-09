package com.example.orderservice.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEventDTO {
    @NotNull
    private float amount;
    @NotNull
    private Long orderId;
    @NotNull
    private String paymentAddress;
    @NotNull
    private String cardInformation;
}
