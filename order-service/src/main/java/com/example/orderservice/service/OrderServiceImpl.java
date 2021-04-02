package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.PaymentInformation;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public Order findOrderByIdAndVersion(Long orderId, Long version) {
        return orderRepository.findByIdAndVersion(orderId, version).orElse(null);
    }

    @Override
    public Page<Order> findAll(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long orderId) {

    }

    @Override
    public void processOrder(Long orderId, PaymentInformation paymentInformation) {

    }
}
