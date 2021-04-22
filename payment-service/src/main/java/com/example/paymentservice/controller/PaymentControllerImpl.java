package com.example.paymentservice.controller;

import com.example.paymentservice.common.response.ResponseDTO;
import com.example.paymentservice.controller.dto.PaymentResponseDTO;
import com.example.paymentservice.controller.dto.ReturnPaymentRequestDTO;
import com.example.paymentservice.mapper.PaymentMapper;
import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@ResponseStatus( HttpStatus.OK )
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {
    private PaymentService paymentService;
    private PaymentMapper paymentMapper;

    @Override
    public PaymentResponseDTO findPaymentById(Long paymentId) {
        return paymentMapper.mapToPaymentResponseDTO(paymentService.findPaymentById(paymentId));
    }

    @Override
    public PaymentResponseDTO findPaymentByIdAndVersion(Long paymentId, Long version) {
        return paymentMapper.mapToPaymentResponseDTO(paymentService.findPaymentByIdAndVersion(paymentId,version));
    }

    @Override
    public Page<PaymentResponseDTO> findOrders(int page, int size) {
        List<PaymentResponseDTO> payments = paymentService.findAll(page, size).stream().map(paymentMapper::mapToPaymentResponseDTO).collect(Collectors.toList());
        return new PageImpl(payments);
    }

    @Override
    public ResponseDTO returnPayment(String paymentId, @Valid ReturnPaymentRequestDTO dto) {
        return null;
    }
}
