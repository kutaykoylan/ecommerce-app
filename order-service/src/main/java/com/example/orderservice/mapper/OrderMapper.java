package com.example.orderservice.mapper;

import com.example.orderservice.controller.dto.CreateOrderRequestDTO;
import com.example.orderservice.controller.dto.FindOrderResponseDto;
import com.example.orderservice.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    FindOrderResponseDto mapToDto(Order order);

    List<FindOrderResponseDto> mapToDto(List<Order> orders);

    Order mapToEntity(FindOrderResponseDto findOrderResponseDto);

    List<Order> mapToEntity(List<FindOrderResponseDto> findOrderResponseDtos);

    Order mapToEntity(CreateOrderRequestDTO createOrderRequestDTO);

}
