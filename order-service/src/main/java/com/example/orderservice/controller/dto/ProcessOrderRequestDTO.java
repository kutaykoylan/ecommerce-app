package com.example.orderservice.controller.dto;


import com.example.orderservice.entity.PaymentInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessOrderRequestDTO {
    private PaymentInformation paymentInformation;
}
