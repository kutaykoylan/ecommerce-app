package com.example.stockservice.controller;

import com.example.stockservice.controller.dto.AddStockRequestDTO;
import com.example.stockservice.controller.dto.CreateStockRequestDTO;
import com.example.stockservice.entity.Stock;
import com.example.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockController implements StockControllerApi {
    private final StockService stockService;

    @Override
    public List<Stock> findStocks() {
        return stockService.findStocks();
    }

    @Override
    public Stock findStockById(Long stockId) {
        return stockService.findStockById(stockId);
    }

    @Override
    public Stock createStock(@Valid CreateStockRequestDTO createStockRequestDTO) {
        return stockService.createStock(createStockRequestDTO);
    }

    @Override
    public Stock addStock(Long stockId, @Valid AddStockRequestDTO addStockRequestDTO) {
        return stockService.addStock(addStockRequestDTO);
    }
}
