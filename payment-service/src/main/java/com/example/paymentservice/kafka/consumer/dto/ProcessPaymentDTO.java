package com.example.paymentservice.kafka.consumer.dto;

import com.example.paymentservice.entity.PaymentInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessPaymentDTO {
    private Long orderId;
    private PaymentInformation paymentInformation;
}
