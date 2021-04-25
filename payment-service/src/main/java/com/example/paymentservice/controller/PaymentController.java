package com.example.paymentservice.controller;

import com.example.paymentservice.common.exception.PaymentException;
import com.example.paymentservice.controller.dto.PaymentResponseDTO;
import com.example.paymentservice.controller.dto.ReturnPaymentRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


public interface PaymentController {
    @GetMapping(value = "/payments/{paymentId}")
    PaymentResponseDTO findPaymentById(@PathVariable("paymentId") Long paymentId);

    @GetMapping(value = "/payments/{paymentId}/{version}")
    PaymentResponseDTO findPaymentByIdAndVersion(@PathVariable("paymentId") Long paymentId, @PathVariable("version") Long version) ;

    @GetMapping(value = "/payments")
    Page<PaymentResponseDTO> findOrders(@RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "size", required = false, defaultValue = "10") int size);

    @PostMapping(value = "/payments/{paymentId}/return")
    PaymentResponseDTO returnPayment(@PathVariable("paymentId") String paymentId, @RequestBody @Valid ReturnPaymentRequestDTO dto) throws PaymentException;
}
