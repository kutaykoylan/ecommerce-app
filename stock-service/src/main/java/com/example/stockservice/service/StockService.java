package com.example.stockservice.service;

import com.example.stockservice.controller.dto.AddStockRequestDTO;
import com.example.stockservice.entity.Stock;
import org.springframework.data.domain.Page;


public interface StockService {
    Stock findById(Long stockId) ;

    Page<Stock> findAll(int page, int size);

    Stock createStock(Stock stock);

    Stock addStock(AddStockRequestDTO addStockRequestDTO);
}
