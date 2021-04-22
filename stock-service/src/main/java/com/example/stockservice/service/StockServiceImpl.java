package com.example.stockservice.service;

import com.example.stockservice.controller.dto.AddStockRequestDTO;
import com.example.stockservice.entity.Stock;
import com.example.stockservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;

    @Override
    public Stock findById(Long stockId) { return stockRepository.findById(stockId).orElse(null); }

    @Override
    public Page<Stock> findAll(int page, int size) { return stockRepository.findAll(PageRequest.of(page, size)); }

    @Override
    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock addStock(AddStockRequestDTO addStockRequestDTO) {
        Stock stock = stockRepository.findById(addStockRequestDTO.getStockId()).orElse(null);
        addStock(addStockRequestDTO.getStockToAdd(), stock);
        return stockRepository.save(stock);
    }

    private void addStock(Long stockToAdd, Stock stock) {
        long currentStock = stock.getRemainingStock();
        currentStock +=stockToAdd;
        stock.setRemainingStock(currentStock);
    }
}
