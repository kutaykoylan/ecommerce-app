package com.example.orderservice.kafka.consumer;

import com.example.orderservice.common.exception.OrderException;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderState;
import com.example.orderservice.kafka.dto.PaymentEventDTO;
import com.example.orderservice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderConsumerService {
    private final OrderService orderService;

    @KafkaListener(topics = "success-payment", groupId = "order-service")
    public void successPayment(String message) throws JsonProcessingException, OrderException {
        PaymentEventDTO paymentSuccessEvent = getProcessPaymentDTO(message);
        Order order = orderService.findOrderById(paymentSuccessEvent.getOrderId());
        if (order.getState() == OrderState.PAYMENT_READY) {
            System.out.println("Payment is processing");
            orderService.setOrderState(order.getId(), OrderState.PAID);
        } else
            throw new OrderException("Order state is not valid for this Operation");
    }

    @KafkaListener(topics = "fail-payment", groupId = "order-service")
    public void failPayment(String message) throws JsonProcessingException, OrderException {
        PaymentEventDTO paymentFailEvent = getProcessPaymentDTO(message);
        Order order = orderService.findOrderById(paymentFailEvent.getOrderId());
        if (order.getState() == OrderState.PAYMENT_READY) {
            System.out.println("Payment is processing");
            orderService.setOrderState(order.getId(), OrderState.PROCESSING_FAILED);
        } else
            throw new OrderException("Order state is not valid for this Operation");
    }

    private PaymentEventDTO getProcessPaymentDTO(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(message, PaymentEventDTO.class);
    }

}
