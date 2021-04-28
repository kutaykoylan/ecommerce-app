package com.example.paymentservice.service;

import com.example.paymentservice.common.exception.PaymentException;
import com.example.paymentservice.entity.Payment;
import org.springframework.data.domain.Page;


public interface PaymentService {
    Payment findPaymentById(Long id) throws PaymentException;

    Payment findPaymentByIdAndVersion(Long paymentId,Long version) throws PaymentException;

    Page<Payment> findAll(int page, int size);

    Payment returnPayment(Payment payment) throws PaymentException;

    Payment createPayment(Payment payment);

    Payment savePayment(Payment payment);
}
