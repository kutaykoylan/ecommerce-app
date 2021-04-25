package com.example.paymentservice.controller.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnPaymentRequestDTO {
    private Long orderId;

    private String paymentId;

    private float amount;
}
