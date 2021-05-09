package com.example.orderservice.kafka.consumer;

import com.example.orderservice.common.exception.OrderException;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderState;
import com.example.orderservice.kafka.dto.PaymentEventDTO;
import com.example.orderservice.kafka.dto.ReturnPaymentEventDTO;
import com.example.orderservice.service.OrderService;
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
public class OrderConsumerService {
    private final OrderService orderService;

    @KafkaListener(topics = "success-payment", groupId = "order-service")
    public PaymentEventDTO successPayment(String message) throws JsonProcessingException, OrderException {
        // TODO review this for error messages
        PaymentEventDTO paymentSuccessEvent = getProcessPaymentDTO(message);
        Order order = orderService.findOrderById(paymentSuccessEvent.getOrderId());
        if (order.getState() == OrderState.PAYMENT_READY) {
            System.out.println("Payment is processing");
            orderService.setOrderState(order.getId(), OrderState.PAID);
            return paymentSuccessEvent;
        } else
            throw new OrderException("Order state is not valid for this Operation");
    }

    @KafkaListener(topics = "fail-payment", groupId = "order-service")
    public PaymentEventDTO failPayment(String message) throws JsonProcessingException, OrderException {
        PaymentEventDTO paymentFailEvent = getProcessPaymentDTO(message);
        Order order = orderService.findOrderById(paymentFailEvent.getOrderId());
        if (order.getState() == OrderState.PAYMENT_READY) {
            System.out.println("Payment is processing");
            orderService.setOrderState(order.getId(), OrderState.PROCESSING_FAILED);
            return paymentFailEvent;
        } else
            throw new OrderException("Order state is not valid for this Operation");
    }

    @KafkaListener(topics = "return-payment", groupId = "order-service")
    public ReturnPaymentEventDTO returnPayment(String message) throws JsonProcessingException, OrderException {
        ReturnPaymentEventDTO returnPaymentEventDTO = getReturnPaymentDTO(message);
        Order order = orderService.findOrderById(returnPaymentEventDTO.getOrderId());
        if (order.getState() != OrderState.CANCELLED) {
            throw new OrderException("Order state is not valid for this Operation");
        } else {
            System.out.println("Order is returned");
            orderService.setOrderState(order.getId(),  OrderState.RETURNED);
            return returnPaymentEventDTO;
        }
    }

    private PaymentEventDTO getProcessPaymentDTO(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PaymentEventDTO paymentEventDTO= mapper.readValue(message, PaymentEventDTO.class);
        validatePaymentEventDTO(paymentEventDTO);
        return paymentEventDTO;
    }

    private ReturnPaymentEventDTO getReturnPaymentDTO(String message) throws  JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ReturnPaymentEventDTO returnPaymentEventDTO = mapper.readValue(message, ReturnPaymentEventDTO.class);
        validateReturnPaymentDTO(returnPaymentEventDTO);
        return returnPaymentEventDTO;
    }

    private void validatePaymentEventDTO(PaymentEventDTO paymentEventDTO) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PaymentEventDTO>> violations = validator.validate(paymentEventDTO);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
    }

    private void validateReturnPaymentDTO(ReturnPaymentEventDTO returnPaymentEventDTO) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ReturnPaymentEventDTO>> violations = validator.validate(returnPaymentEventDTO);
        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }
    }

}
