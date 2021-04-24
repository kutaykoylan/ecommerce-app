package com.example.orderservice.kafka.dto;

import com.example.orderservice.entity.PaymentInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessPaymentDTO {
    private PaymentInformation paymentInformation;
}
