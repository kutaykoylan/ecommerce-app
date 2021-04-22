package com.example.stockservice.controller.dto;

import com.example.stockservice.entity.StockState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockResponseDTO {
    private Long id;

    private String stockName;

    private long remainingStock;

    private long price;

    private StockState state;
}
