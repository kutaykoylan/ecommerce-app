package com.example.paymentservice.kafka.consumer.dto;

import com.example.paymentservice.entity.PaymentInformation;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessPaymentDTO {
     Long orderId;
     PaymentInformation paymentInformation;
}
