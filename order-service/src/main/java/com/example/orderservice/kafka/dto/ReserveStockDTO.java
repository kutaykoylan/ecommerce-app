package com.example.orderservice.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveStockDTO {
    private String stockId;
    private int orderAmount;
}
