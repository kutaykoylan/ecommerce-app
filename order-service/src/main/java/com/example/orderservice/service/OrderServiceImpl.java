package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.PaymentInformation;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

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

    // Should call kafka to send reserve stock event
    @Override
    public Order createOrder(Order order) {
        String stockId = order.getStockId();
        kafkaTemplate.send("reserve-stock", stockId);
        return orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long orderId) {

    }

    @Override
    public void processOrder(Long orderId, PaymentInformation paymentInformation) {

    }
}
