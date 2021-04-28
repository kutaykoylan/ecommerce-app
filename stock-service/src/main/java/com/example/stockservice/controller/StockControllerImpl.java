package com.example.stockservice.controller;

import com.example.stockservice.common.exception.BadRequestException;
import com.example.stockservice.common.exception.StockException;
import com.example.stockservice.controller.dto.*;
import com.example.stockservice.entity.Stock;
import com.example.stockservice.mapper.StockMapper;
import com.example.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Log4j2
public class StockControllerImpl implements StockController {
    private final StockService stockService;
    private final StockMapper stockMapper;

    @Override
    public Page<StockResponseDTO> findStocks(int page, int size) {
        List<StockResponseDTO> orders = stockService.findAll(page, size).stream().map(stockMapper::mapToStockResponseDTO).collect(Collectors.toList());
        return new PageImpl(orders);

    }

    @Override
    public StockResponseDTO findStockById(Long stockId) throws StockException {
        return stockMapper.mapToStockResponseDTO(stockService.findStockById(stockId));
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
    public ResponseEntity<StockResponseDTO> addStock(Long stockId, @Valid AddStockRequestDTO addStockRequestDTO) throws StockException {
        return new ResponseEntity<>(stockMapper.mapToStockResponseDTO(stockService.addStock(addStockRequestDTO)),HttpStatus.OK);
    }

    @ExceptionHandler({StockException.class})
    public void handleException(Exception e, HttpServletRequest request) {
        String error = e.getMessage();
        log.error(request.getRequestURI(),error);
    }
}
