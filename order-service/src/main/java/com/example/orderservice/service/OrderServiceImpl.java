package com.example.orderservice.service;

import com.example.orderservice.common.exception.OrderException;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderState;
import com.example.orderservice.entity.PaymentInformation;
import com.example.orderservice.kafka.dto.ProcessPaymentDTO;
import com.example.orderservice.kafka.dto.ReleaseStockDTO;
import com.example.orderservice.kafka.dto.ReserveStockDTO;
import com.example.orderservice.kafka.producer.OrderProducer;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

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
    public void cancelOrder(Long orderId) throws OrderException {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order.getState() != OrderState.PAID) {
            throw new OrderException("Order state is not valid for this operation");
        }
        String stockId = order.getStockId();
        ReleaseStockDTO releaseStockDTO = new ReleaseStockDTO(stockId, order.getOrderAmount());
        // TODO: check if this works
        setOrderState(order.getId(), OrderState.CANCELLED);
        orderProducer.sendReleaseStockEvent(releaseStockDTO);
    }

    @Override
    public void processOrder(Long orderId, PaymentInformation paymentInformation) throws OrderException {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order.getState() != OrderState.INITIAL) {
            throw new OrderException("Order state is not valid for this operation");
        }
        String stockId = order.getStockId();
        ReserveStockDTO reserveStockDTO = new ReserveStockDTO(stockId, order.getOrderAmount());
        ProcessPaymentDTO processPaymentDTO = new ProcessPaymentDTO(orderId,paymentInformation);
        order.setState(OrderState.PAYMENT_READY);
        orderRepository.save(order);
        orderProducer.sendReserveStockEvent(reserveStockDTO);
        orderProducer.sendProcessPaymentEvent(processPaymentDTO);
    }

    @Override
    public void setOrderState(Long orderId, OrderState orderState) {
        Order order = orderRepository.findById(orderId).orElse(null);
        order.setState(orderState);
        orderRepository.save(order);
    }
}
