package com.example.paymentservice.service;

import com.example.paymentservice.entity.Payment;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PaymentService {
    Payment findPaymentById(Long id);

    Payment findPaymentByIdAndVersion(Long paymentId,Long version);

    Page<Payment> findAll(int page, int size);
}
