package com.example.paymentservice.kafka.consumer.dto;

import com.example.paymentservice.entity.PaymentInformation;
import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessPaymentDTO {
     Long orderId;
     PaymentInformation paymentInformation;
}
