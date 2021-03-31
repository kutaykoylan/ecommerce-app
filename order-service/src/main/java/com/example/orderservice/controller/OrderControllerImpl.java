package com.example.orderservice.controller;

import com.example.orderservice.common.exception.BadRequestException;
import com.example.orderservice.common.exception.OrderException;
import com.example.orderservice.common.response.ResponseDTO;
import com.example.orderservice.controller.dto.CreateOrderRequestDTO;
import com.example.orderservice.controller.dto.FindOrderResponseDto;
import com.example.orderservice.controller.dto.ProcessOrderRequestDTO;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@ResponseStatus( HttpStatus.OK )
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @Override
    public FindOrderResponseDto findOrderById(Long orderId) {
        return orderMapper.mapToDto(orderService.findOrderById(orderId));
    }

    @Override
    public FindOrderResponseDto findOrderByIdAndVersion(Long orderId, Long version)  {
        return orderMapper.mapToDto(orderService.findOrderByIdAndVersion(orderId,version));
    }

    @Override
    public Page<FindOrderResponseDto> findOrders(int page, int size) {
        List<FindOrderResponseDto> orders = orderService.findAll(page, size).stream().map(orderMapper::mapToDto).collect(Collectors.toList());
        return new PageImpl(orders);
    }

    @Override
    public ResponseEntity<ResponseDTO> createOrder(@Valid CreateOrderRequestDTO createOrderRequestDTO) {
        if (createOrderRequestDTO == null) {
            throw new BadRequestException("You send an empty activity");
        } else{
            orderService.createOrder(orderMapper.mapToEntity(createOrderRequestDTO));
            return new ResponseEntity<>(new ResponseDTO("Order is created successfully!"),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> cancelOrder(String orderId) throws OrderException {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO> processOrder(String orderId, @Valid ProcessOrderRequestDTO dto) throws OrderException {
        return null;
    }
}
