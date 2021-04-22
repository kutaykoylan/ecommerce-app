package com.example.stockservice.mapper;

import com.example.stockservice.controller.dto.CreateStockRequestDTO;
import com.example.stockservice.controller.dto.StockResponseDTO;
import com.example.stockservice.entity.Stock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockResponseDTO mapToStockResponseDTO(Stock stock);

    Stock mapToEntity(CreateStockRequestDTO createStockRequestDTO);

}
