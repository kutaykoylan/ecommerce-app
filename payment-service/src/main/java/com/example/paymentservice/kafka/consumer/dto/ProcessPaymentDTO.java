package com.example.paymentservice.kafka.consumer.dto;

import com.example.paymentservice.entity.PaymentInformation;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessPaymentDTO {
     @NotNull
     Long orderId;
     @NotNull
     PaymentInformation paymentInformation;
}
