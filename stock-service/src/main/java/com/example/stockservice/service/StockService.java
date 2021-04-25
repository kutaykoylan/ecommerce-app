package com.example.stockservice.service;

import com.example.stockservice.controller.dto.AddStockRequestDTO;
import com.example.stockservice.entity.Stock;
import com.example.stockservice.kafka.dto.ReserveStockDTO;
import org.springframework.data.domain.Page;


public interface StockService {
    Stock findStockById(Long stockId) ;

    Page<Stock> findAll(int page, int size);

    Stock createStock(Stock stock);

    Stock addStock(AddStockRequestDTO addStockRequestDTO);

    Stock decreaseStock(ReserveStockDTO reserveStockDTO);
}
