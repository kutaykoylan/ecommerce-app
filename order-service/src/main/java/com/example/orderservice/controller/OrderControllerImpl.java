package com.example.orderservice.controller;

import com.example.orderservice.controller.dto.OrderDto;
import com.example.orderservice.controller.mapper.OrderMapper;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@ResponseStatus( HttpStatus.OK )
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @Override
    public OrderDto findOrderById(Long orderId) {
        return orderMapper.mapToDto(orderService.findOrderById(orderId));
    }

    @Override
    public OrderDto findOrderByIdAndVersion(Long orderId, Long version)  {
        return orderMapper.mapToDto(orderService.findOrderByIdAndVersion(orderId,version));
    }

    @Override
    public Page<OrderDto> findOrders(int page, int size) {
        List<OrderDto> orders = orderService.findAll(page, size).stream().map(orderMapper::mapToDto).collect(Collectors.toList());
        return new PageImpl(orders);
    }
}
