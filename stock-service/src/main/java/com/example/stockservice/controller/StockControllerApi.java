package com.example.stockservice.controller;

import com.example.stockservice.controller.dto.AddStockRequestDTO;
import com.example.stockservice.controller.dto.CreateStockRequestDTO;
import com.example.stockservice.entity.Stock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface StockControllerApi {

    @GetMapping(value = "/stock/")
    List<Stock> findStocks();

    @GetMapping(value = "/stock/{stockId}")
    Stock findStockById(@PathVariable("stockId") Long stockId);

    @PostMapping(value = "/stock/")
    Stock createStock(@RequestBody @Valid CreateStockRequestDTO createStockRequestDTO);

    @PostMapping(value = "/stock/{stockId}")
    Stock addStock(@PathVariable("stockId") Long stockId, @RequestBody @Valid AddStockRequestDTO addStockRequestDTO);
}
