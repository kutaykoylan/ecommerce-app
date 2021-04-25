package com.example.paymentservice.controller;

import com.example.paymentservice.common.exception.PaymentException;
import com.example.paymentservice.controller.dto.PaymentResponseDTO;
import com.example.paymentservice.controller.dto.ReturnPaymentRequestDTO;
import com.example.paymentservice.mapper.PaymentMapper;
import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@ResponseStatus( HttpStatus.OK )
@RequiredArgsConstructor
@Log4j2
public class PaymentControllerImpl implements PaymentController {
    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

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
    public PaymentResponseDTO returnPayment(String paymentId, @Valid ReturnPaymentRequestDTO dto) throws PaymentException {
        return paymentMapper.mapToPaymentResponseDTO(paymentService.returnPayment(paymentMapper.mapToEntity(dto)));
    }

    @ExceptionHandler({PaymentException.class})
    public void handleException(Exception e, HttpServletRequest request) {
        String error = e.getMessage();
        log.error(request.getRequestURI(),error);
    }
}
