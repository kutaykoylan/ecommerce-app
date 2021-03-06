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
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@ResponseStatus( HttpStatus.OK )
@RequiredArgsConstructor
@Log4j2
public class OrderControllerImpl implements OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Override
    public FindOrderResponseDTO findOrderById(Long orderId) throws OrderException {
        return orderMapper.mapToFindOrderResponseDTO(orderService.findOrderById(orderId));
    }

    @Override
    public FindOrderResponseDTO findOrderByIdAndVersion(Long orderId, Long version) throws OrderException {
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
            throw new BadRequestException("You send an empty Order");
        } else{
            Order orderThatIsSaved = orderService.createOrder(orderMapper.mapToEntity(createOrderRequestDTO));
            return new ResponseEntity<>(orderMapper.mapToCreateOrderResponseDTO(orderThatIsSaved),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> cancelOrder(Long orderId) throws OrderException {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(new ResponseDTO("Order is cancelled"),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDTO> processOrder(Long orderId, @Valid ProcessOrderRequestDTO dto) throws OrderException {
        orderService.processOrder(orderId,dto.getPaymentInformation());
        return new ResponseEntity<>(new ResponseDTO("Order is processed"),HttpStatus.OK);
    }

    @ExceptionHandler({OrderException.class})
    public void handleException(Exception e, HttpServletRequest request) {
        String error = e.getMessage();
        log.error(request.getRequestURI(),error);
    }
}
