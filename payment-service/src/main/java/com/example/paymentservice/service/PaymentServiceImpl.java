package com.example.paymentservice.service;

import com.example.paymentservice.common.exception.PaymentException;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.entity.PaymentState;
import com.example.paymentservice.kafka.producer.PaymentProducerService;
import com.example.paymentservice.kafka.producer.dto.ReturnPaymentEventDTO;
import com.example.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;
    private final PaymentProducerService paymentProducerService;

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

    @Override
    public Payment returnPayment(Payment payment) throws PaymentException {
        Optional<Payment> paymentVt = paymentRepository.findById(payment.getOrderId());
         if (paymentVt.isPresent()){
             returnPaymentWhenIsPresent(paymentVt.get());
             return paymentVt.get();
         }else
             throw new PaymentException("There is no payment available with that id");
    }

    private void returnPaymentWhenIsPresent(Payment paymentVt) throws PaymentException {
        if(paymentVt.getState() != PaymentState.PAID)
            throw new PaymentException("Payment state is not valid for this Operation: " + paymentVt.toString());
        ReturnPaymentEventDTO returnedPaymentEventDTO = ReturnPaymentEventDTO.builder()
                .orderId(paymentVt.getOrderId())
                .amount(paymentVt.getAmount())
                .build();
        paymentProducerService.sendReturnPaymentEvent(returnedPaymentEventDTO);
    }
}
