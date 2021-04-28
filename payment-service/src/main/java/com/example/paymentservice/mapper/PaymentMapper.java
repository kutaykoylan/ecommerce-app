package com.example.paymentservice.mapper;

import com.example.paymentservice.controller.dto.PaymentResponseDTO;
import com.example.paymentservice.controller.dto.ReturnPaymentRequestDTO;
import com.example.paymentservice.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponseDTO mapToPaymentResponseDTO(Payment payment);
    @Mapping(source = "paymentId" ,target = "id")
    Payment mapToEntity(ReturnPaymentRequestDTO returnPaymentRequestDTO);
}
