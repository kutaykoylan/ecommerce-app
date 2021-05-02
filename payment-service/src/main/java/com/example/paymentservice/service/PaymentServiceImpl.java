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
    public Payment findPaymentById(Long id) throws PaymentException {
        Optional<Payment> paymentVt = paymentRepository.findById(id);
        if (paymentVt.isPresent()){
            return paymentVt.get();
        }else
            throw new PaymentException("There is no payment available with that id");
    }

    @Override
    public Page<Payment> findAll(int page, int size) {
        return paymentRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Payment findPaymentByIdAndVersion(Long paymentId, Long version) throws PaymentException {
        Optional<Payment> paymentVt = paymentRepository.findByIdAndVersion(paymentId,version);
        if (paymentVt.isPresent()){
            return paymentVt.get();
        }else
            throw new PaymentException("There is no payment available with that id and history");
    }

    @Override
    public Payment returnPayment(Payment payment) throws PaymentException {
        Optional<Payment> paymentVt = paymentRepository.findById(payment.getId());
         if (paymentVt.isPresent()){
             returnPaymentWhenIsPresent(paymentVt.get());
             return paymentVt.get();
         }else
             throw new PaymentException("There is no payment available with that id");
    }

    @Override
    public Payment createPayment(Payment payment) { return paymentRepository.save(payment); }

    private void returnPaymentWhenIsPresent(Payment paymentVt) throws PaymentException {
        if(paymentVt.getState() != PaymentState.PAID)
            throw new PaymentException("Payment state is not valid for this Operation: " + paymentVt.toString());
        paymentVt.setState(PaymentState.RETURN);
        paymentRepository.save(paymentVt);
        ReturnPaymentEventDTO returnedPaymentEventDTO = ReturnPaymentEventDTO.builder()
                .orderId(paymentVt.getOrderId())
                .amount(paymentVt.getAmount())
                .build();
        paymentProducerService.sendReturnPaymentEvent(returnedPaymentEventDTO);
    }

    @Override
    public Payment savePayment(Payment payment) { return paymentRepository.save(payment); }
}
