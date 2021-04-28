package com.example.stockservice.service;

import com.example.stockservice.common.exception.StockException;
import com.example.stockservice.controller.dto.AddStockRequestDTO;
import com.example.stockservice.entity.Stock;
import com.example.stockservice.kafka.dto.ReserveStockDTO;
import com.example.stockservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;

    @Override
    public Stock findStockById(Long stockId) throws StockException {
        Optional<Stock> stockVt = stockRepository.findById(stockId);
        if (stockVt.isPresent()){
            return stockVt.get();
        }else
            throw new StockException("There is no stock available with that id");
    }

    @Override
    public Page<Stock> findAll(int page, int size) { return stockRepository.findAll(PageRequest.of(page, size)); }

    @Override
    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock addStock(AddStockRequestDTO addStockRequestDTO) throws StockException {
        Optional<Stock> stockVt = stockRepository.findById(addStockRequestDTO.getStockId());
        if (stockVt.isPresent()){
            Stock stock = stockVt.get();
            addStock(addStockRequestDTO.getStockToAdd(), stock);
            return stockRepository.save(stock);
        }else
            throw new StockException("There is no stock available with that id");
    }

    @Override
    public Stock decreaseStock(ReserveStockDTO reserveStockDTO) throws StockException {
        Optional<Stock> stockVt = stockRepository.findById(Long.parseLong(reserveStockDTO.getStockId()));
        if (stockVt.isPresent()){
            Stock stock = stockVt.get();
            decreaseStock(reserveStockDTO.getOrderAmount(), stock);
        //    System.out.println(stock.getId());
            return stockRepository.save(stock);
        }else
            throw new StockException("There is no stock available with that id");

    }

    private void addStock(Long stockToAdd, Stock stock) {
        long currentStock = stock.getRemainingStock();
        currentStock +=stockToAdd;
        stock.setRemainingStock(currentStock);
    }

    private void decreaseStock(Long stockToDecrease, Stock stock) {
        long currentStock = stock.getRemainingStock();
        currentStock -= stockToDecrease;
        stock.setRemainingStock(currentStock);
    }

}
