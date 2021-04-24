package com.example.stockservice.service;

import com.example.stockservice.controller.dto.AddStockRequestDTO;
import com.example.stockservice.controller.dto.CreateStockRequestDTO;
import com.example.stockservice.entity.Stock;
import com.example.stockservice.entity.StockState;
import com.example.stockservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    public Stock findStockById(Long stockId) { return stockRepository.findById(stockId).orElse(null); }

    public List<Stock> findStocks() { return stockRepository.findAll(); }

    public Stock createStock(CreateStockRequestDTO createStockDto) {
        Stock stock = new Stock();
        StockState stockState = StockState.INUSE;
        stock.setStockName(createStockDto.getStockName());
        stock.setRemainingStock(createStockDto.getRemainingStock());
        stock.setPrice(createStockDto.getPrice());
        stock.setState(stockState);
        return stockRepository.save(stock);
    }

    public Stock addStock(AddStockRequestDTO addStockRequestDTO) {
        Stock stock = stockRepository.findById(addStockRequestDTO.getStockId()).orElse(null);
        long currentStock = stock.getRemainingStock();
        currentStock += addStockRequestDTO.getStockToAdd();
        stock.setRemainingStock(currentStock);
        return stockRepository.save(stock);
    }

    public void decreaseStock(String stockId) {
        Stock stock = stockRepository.findById(Long.parseLong(stockId)).orElse(null);
        long remainingStock = stock.getRemainingStock();
        remainingStock -= 1;
        stock.setRemainingStock(remainingStock);
        stockRepository.save(stock);
    }
}
