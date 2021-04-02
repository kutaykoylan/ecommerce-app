package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.PaymentInformation;
import org.springframework.data.domain.Page;

public interface OrderService {

    Order findOrderById(Long orderId);

    Order findOrderByIdAndVersion(Long orderId,Long version);

    Order createOrder(Order order);

    void cancelOrder(Long orderId);

    void processOrder(Long orderId, PaymentInformation paymentInformation);

    Page<Order> findAll(int page, int size);
}
