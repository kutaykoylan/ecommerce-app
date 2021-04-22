package com.example.stockservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStockRequestDTO {
    private String stockName;
    private int remainingStock;
    private int price;
}