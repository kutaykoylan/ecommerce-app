package com.example.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInformation {

    private float amount;

    private String paymentAddress;

    private String cardInformation;

}