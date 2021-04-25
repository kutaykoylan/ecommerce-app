package com.example.orderservice.service;

import com.example.orderservice.common.exception.OrderException;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderState;
import com.example.orderservice.entity.PaymentInformation;
import org.springframework.data.domain.Page;

public interface OrderService {

    Order findOrderById(Long orderId);

    Order findOrderByIdAndVersion(Long orderId,Long version);

    Order createOrder(Order order);

    void cancelOrder(Long orderId) throws OrderException;

    void processOrder(Long orderId, PaymentInformation paymentInformation) throws OrderException;

    void setOrderState(Long orderId, OrderState orderState);

    Page<Order> findAll(int page, int size);
}
