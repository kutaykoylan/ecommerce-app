package com.example.paymentservice.kafka.consumer;

import com.example.paymentservice.common.exception.PaymentException;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.entity.PaymentState;
import com.example.paymentservice.kafka.consumer.dto.ProcessPaymentDTO;
import com.example.paymentservice.kafka.producer.PaymentProducerService;
import com.example.paymentservice.kafka.producer.dto.PaymentEventDTO;
import com.example.paymentservice.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class PaymentConsumerService {
    private final PaymentProducerService paymentProducerService;
    private final PaymentService paymentService;

    @KafkaListener(topics = "process-payment", groupId = "payment-service")
    public ProcessPaymentDTO processPayment(String message) throws JsonProcessingException, PaymentException {
        System.out.println(message);
        ProcessPaymentDTO processPaymentDTO = getProcessPaymentDTO(message);
        Payment payment = getPaymentObjectFromProcessPaymentDTO(processPaymentDTO);
        paymentService.createPayment(payment);
        PaymentEventDTO successfulPaymentEventDTO = PaymentEventDTO.builder()
                .paymentAddress(processPaymentDTO.getPaymentInformation().getPaymentAddress())
                .orderId(processPaymentDTO.getOrderId())
                .cardInformation(processPaymentDTO.getPaymentInformation().getCardInformation())
                .build();
        if(processPaymentDTO.getPaymentInformation().getAmount()>2000)
            throw new PaymentException("Payment amount cannot be more than 2000");
        else if(processPaymentDTO.getPaymentInformation().getAmount()>1000){
            PaymentEventDTO failedPaymentEventDTO = PaymentEventDTO.builder()
                    .orderId(processPaymentDTO.getOrderId())
                    .paymentAddress(processPaymentDTO.getPaymentInformation().getPaymentAddress())
                    .build();
            paymentProducerService.sendFailPaymentEvent(failedPaymentEventDTO);
            payment.setState(PaymentState.FAILED);
            paymentService.savePayment(payment);
        } else {
            paymentProducerService.sendSucessPaymentEvent(successfulPaymentEventDTO);
            payment.setState(PaymentState.PAID);
            paymentService.savePayment(payment);
        }
        return processPaymentDTO;
    }

    private ProcessPaymentDTO getProcessPaymentDTO(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ProcessPaymentDTO processPaymentDTO = mapper.readValue(message, ProcessPaymentDTO.class);
        validateProcessPaymentDTO(processPaymentDTO);
        return processPaymentDTO;
    }

    private void validateProcessPaymentDTO(ProcessPaymentDTO processPaymentDTO) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ProcessPaymentDTO>> violations = validator.validate(processPaymentDTO);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
    }

    private Payment getPaymentObjectFromProcessPaymentDTO(ProcessPaymentDTO processPaymentDTO) {
        Payment payment = new Payment();
        payment.setAmount(processPaymentDTO.getPaymentInformation().getAmount());
        payment.setCardInformation(processPaymentDTO.getPaymentInformation().getCardInformation());
        payment.setPaymentAddress(processPaymentDTO.getPaymentInformation().getPaymentAddress());
        payment.setOrderId(processPaymentDTO.getOrderId());
        return payment;
    }
}