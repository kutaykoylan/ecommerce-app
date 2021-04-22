package com.example.stockservice.controller;

import com.example.stockservice.common.exception.BadRequestException;
import com.example.stockservice.common.response.ResponseDTO;
import com.example.stockservice.controller.dto.*;
import com.example.stockservice.entity.Stock;
import com.example.stockservice.mapper.StockMapper;
import com.example.stockservice.service.StockService;
import com.example.stockservice.service.StockServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class StockControllerImpl implements StockController {
    private final StockService stockService;

    private final StockMapper stockMapper;

    @Override
    public Page<StockResponseDTO> findStocks(int page, int size) {
        List<StockResponseDTO> orders = stockService.findAll(page, size).stream().map(stockMapper::mapToStockResponseDTO).collect(Collectors.toList());
        return new PageImpl(orders);

    }

    @Override
    public StockResponseDTO findStockById(Long stockId) {
        return stockMapper.mapToStockResponseDTO(stockService.findById(stockId));
    }

    @Override
    public ResponseEntity<StockResponseDTO> createStock(@Valid CreateStockRequestDTO createStockRequestDTO) {
        if (createStockRequestDTO == null) {
            throw new BadRequestException("You send an empty Stock");
        } else{
            Stock stockThatIsSaved = stockService.createStock(stockMapper.mapToEntity(createStockRequestDTO));
            return new ResponseEntity<>(stockMapper.mapToStockResponseDTO(stockThatIsSaved), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<StockResponseDTO> addStock(Long stockId, @Valid AddStockRequestDTO addStockRequestDTO) {
        return new ResponseEntity<>(stockMapper.mapToStockResponseDTO(stockService.addStock(addStockRequestDTO)),HttpStatus.OK);
    }
}
