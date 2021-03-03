package com.example.orderservice.controller;


import com.example.orderservice.controller.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
//import com.querydsl.core.types.Predicate;

public interface OrderController {

    @GetMapping(value = "/orders/{orderId}")
    OrderDto findOrderById(@PathVariable("orderId") Long orderId);

    @GetMapping(value = "/orders/{orderId}/{version}")
    OrderDto findOrderByIdAndVersion(@PathVariable("orderId") Long orderId, @PathVariable("version") Long version) ;


    @GetMapping(value = "/orders")
    Page<OrderDto> findOrders(@RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "size", required = false, defaultValue = "10") int size);


}
