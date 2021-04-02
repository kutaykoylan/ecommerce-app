package com.example.orderservice.mapper;

import com.example.orderservice.controller.dto.CreateOrderRequestDTO;
import com.example.orderservice.controller.dto.CreateOrderResponseDTO;
import com.example.orderservice.controller.dto.FindOrderResponseDto;
import com.example.orderservice.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    FindOrderResponseDto mapToFindOrderResponseDTO(Order order);

    CreateOrderResponseDTO mapToCreateOrderResponseDTO(Order order);

    List<FindOrderResponseDto> mapToListOfFindOrderResponseDTO(List<Order> orders);

    Order mapToEntity(FindOrderResponseDto findOrderResponseDto);

    List<Order> mapToListOfEntities(List<FindOrderResponseDto> findOrderResponseDtos);

    Order mapToEntity(CreateOrderRequestDTO createOrderRequestDTO);

}
