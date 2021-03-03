package com.example.orderservice.controller.mapper;

import com.example.orderservice.controller.dto.OrderDto;
import com.example.orderservice.entity.Order;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto mapToDto(Order order);

    List<OrderDto> mapToDto(List<Order> orders);

    Order mapToEntity(OrderDto orderDto);

    List<Order> mapToEntity(List<OrderDto> orderDtos);

}
