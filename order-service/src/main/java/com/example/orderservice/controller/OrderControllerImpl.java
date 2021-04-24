package com.example.orderservice.controller;

import com.example.orderservice.common.exception.BadRequestException;
import com.example.orderservice.common.exception.OrderException;
import com.example.orderservice.common.response.ResponseDTO;
import com.example.orderservice.controller.dto.CreateOrderRequestDTO;
import com.example.orderservice.controller.dto.CreateOrderResponseDTO;
import com.example.orderservice.controller.dto.FindOrderResponseDTO;
import com.example.orderservice.controller.dto.ProcessOrderRequestDTO;
import com.example.orderservice.entity.Order;
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
    public FindOrderResponseDTO findOrderById(Long orderId) {
        return orderMapper.mapToFindOrderResponseDTO(orderService.findOrderById(orderId));
    }

    @Override
    public FindOrderResponseDTO findOrderByIdAndVersion(Long orderId, Long version)  {
        return orderMapper.mapToFindOrderResponseDTO(orderService.findOrderByIdAndVersion(orderId,version));
    }

    @Override
    public Page<FindOrderResponseDTO> findOrders(int page, int size) {
        List<FindOrderResponseDTO> orders = orderService.findAll(page, size).stream().map(orderMapper::mapToFindOrderResponseDTO).collect(Collectors.toList());
        return new PageImpl(orders);
    }

    @Override
    public ResponseEntity<CreateOrderResponseDTO> createOrder(@Valid CreateOrderRequestDTO createOrderRequestDTO) {
        if (createOrderRequestDTO == null) {
            throw new BadRequestException("You send an empty activity");
        } else{
            Order orderThatIsSaved = orderService.createOrder(orderMapper.mapToEntity(createOrderRequestDTO));
            return new ResponseEntity<>(orderMapper.mapToCreateOrderResponseDTO(orderThatIsSaved),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> cancelOrder(String orderId) throws OrderException {
        // Delete order
        // Call kafka to increase stock
        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO> processOrder(String orderId, @Valid ProcessOrderRequestDTO dto) throws OrderException {
        // call kafka to create payment
        // return json response as payment created
        return null;
    }
}
