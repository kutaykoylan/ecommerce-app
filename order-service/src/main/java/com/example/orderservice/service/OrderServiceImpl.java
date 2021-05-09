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
import com.example.orderservice.service.helper.OrderServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final OrderServiceHelper orderServiceHelper;

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> orderVt =orderRepository.findById(orderId);
        if (orderVt.isPresent()){
            return orderVt.get();
        }else
            throw new OrderException("There is no order available with that id");
    }

    @Override
    public Order findOrderByIdAndVersion(Long orderId, Long version) throws OrderException {
        Optional<Order> orderVt =orderRepository.findByIdAndVersion(orderId, version);
        if (orderVt.isPresent()){
            return orderVt.get();
        }else
            throw new OrderException("There is no order available with that id and history");
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
        if (orderServiceHelper.isOrderStatePaid(order)) {
            throw new OrderException("Order state is not valid for this operation");
        }
        String stockId = order.getStockId();
        ReleaseStockDTO releaseStockDTO = new ReleaseStockDTO(stockId, order.getOrderAmount());
        setOrderState(order.getId(), OrderState.CANCELLED);
        orderProducer.sendReleaseStockEvent(releaseStockDTO);
    }



    @Override
    public void processOrder(Long orderId, PaymentInformation paymentInformation) throws OrderException {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (orderServiceHelper.isOrderStateInitial(order)) {
            throw new OrderException("Order state is not valid for this operation");
        }
        String stockId = order.getStockId();
        ReserveStockDTO reserveStockDTO = new ReserveStockDTO(stockId, order.getOrderAmount());
        ProcessPaymentDTO processPaymentDTO = new ProcessPaymentDTO(orderId,paymentInformation);
        updateOrder(paymentInformation, order);
        publishProcessOrderMessages(reserveStockDTO, processPaymentDTO);
    }

    private void updateOrder(PaymentInformation paymentInformation, Order order) {
        order.setState(OrderState.PAYMENT_READY);
        setPaymentInformationOfOrder(paymentInformation, order);
        orderRepository.save(order);
    }

    private void publishProcessOrderMessages(ReserveStockDTO reserveStockDTO, ProcessPaymentDTO processPaymentDTO) {
        orderProducer.sendReserveStockEvent(reserveStockDTO);
        orderProducer.sendProcessPaymentEvent(processPaymentDTO);
    }

    private void setPaymentInformationOfOrder(PaymentInformation paymentInformation, Order order) {
        order.setAmount(paymentInformation.getAmount());
        order.setPaymentAddress(paymentInformation.getPaymentAddress());
        order.setCardInformation(paymentInformation.getCardInformation());
    }

    @Override
    public void setOrderState(Long orderId, OrderState orderState) {
        Order order = orderRepository.findById(orderId).orElse(null);
        order.setState(orderState);
        orderRepository.save(order);
    }
}
