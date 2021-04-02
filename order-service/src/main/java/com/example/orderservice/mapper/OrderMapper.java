package com.example.orderservice.mapper;

import com.example.orderservice.controller.dto.CreateOrderRequestDTO;
import com.example.orderservice.controller.dto.CreateOrderResponseDTO;
import com.example.orderservice.controller.dto.FindOrderResponseDTO;
import com.example.orderservice.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    FindOrderResponseDTO mapToFindOrderResponseDTO(Order order);

    CreateOrderResponseDTO mapToCreateOrderResponseDTO(Order order);

    List<FindOrderResponseDTO> mapToListOfFindOrderResponseDTO(List<Order> orders);

    Order mapToEntity(FindOrderResponseDTO findOrderResponseDto);

    List<Order> mapToListOfEntities(List<FindOrderResponseDTO> findOrderResponseDTOS);

    Order mapToEntity(CreateOrderRequestDTO createOrderRequestDTO);

}
