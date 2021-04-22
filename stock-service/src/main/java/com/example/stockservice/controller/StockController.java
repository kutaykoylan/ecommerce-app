package com.example.stockservice.controller;

import com.example.stockservice.controller.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface StockController {

    @GetMapping(value = "/stocks")
    Page<StockResponseDTO> findStocks(@RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "size", required = false, defaultValue = "10") int size);

    @GetMapping(value = "/stocks/{stockId}")
    StockResponseDTO findStockById(@PathVariable("stockId") Long stockId);

    @PostMapping(value = "/stocks")
    ResponseEntity<StockResponseDTO> createStock(@RequestBody @Valid CreateStockRequestDTO createStockRequestDTO);

    @PostMapping(value = "/stocks/{stockId}")
    ResponseEntity<StockResponseDTO> addStock(@PathVariable("stockId") Long stockId, @RequestBody @Valid AddStockRequestDTO addStockRequestDTO);
}
