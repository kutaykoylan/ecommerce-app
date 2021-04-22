package com.example.paymentservice.service;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private PaymentRepository paymentRepository;

    @Override
    public Payment findPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Payment> findAll(int page, int size) {
        return paymentRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Payment findPaymentByIdAndVersion(Long paymentId, Long version) {
        return paymentRepository.findByIdAndVersion(paymentId,version).orElse(null);
    }
}
