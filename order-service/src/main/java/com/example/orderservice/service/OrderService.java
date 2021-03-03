package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

    Order findOrderById(Long orderId);

    Order findOrderByIdAndVersion(Long orderId,Long version);

    Page<Order> findAll(int page, int size);
}
