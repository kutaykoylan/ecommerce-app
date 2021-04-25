package com.example.orderservice.controller;


import com.example.orderservice.common.exception.OrderException;
import com.example.orderservice.controller.dto.CreateOrderRequestDTO;
import com.example.orderservice.controller.dto.CreateOrderResponseDTO;
import com.example.orderservice.controller.dto.FindOrderResponseDTO;
import com.example.orderservice.common.response.ResponseDTO;
import com.example.orderservice.controller.dto.ProcessOrderRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface OrderController {

    @GetMapping(value = "/orders/{orderId}")
    FindOrderResponseDTO findOrderById(@PathVariable("orderId") Long orderId);

    @GetMapping(value = "/orders/{orderId}/{version}")
    FindOrderResponseDTO findOrderByIdAndVersion(@PathVariable("orderId") Long orderId, @PathVariable("version") Long version) ;

    @GetMapping(value = "/orders")
    Page<FindOrderResponseDTO> findOrders(@RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "size", required = false, defaultValue = "10") int size);

    @PostMapping(value = "/orders")
    ResponseEntity<CreateOrderResponseDTO> createOrder(@RequestBody @Valid CreateOrderRequestDTO createOrderRequestDTO);

    @DeleteMapping(value = "/orders/{orderId}")
    ResponseEntity<ResponseDTO> cancelOrder(@PathVariable("orderId") Long orderId) throws OrderException;

    @PostMapping(value = "/orders/{orderId}/process")
    ResponseEntity<ResponseDTO> processOrder(@PathVariable("orderId") Long orderId, @RequestBody @Valid ProcessOrderRequestDTO dto) throws OrderException;


}
