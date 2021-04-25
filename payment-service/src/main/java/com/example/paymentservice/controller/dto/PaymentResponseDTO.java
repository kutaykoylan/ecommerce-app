package com.example.paymentservice.controller.dto;

import com.example.paymentservice.entity.PaymentState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {

    private Long id;

    private PaymentState state;

    private String paymentAddress;

    private String orderId;

    private float amount;

    private String cardInformation;

}
