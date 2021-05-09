package com.example.stockservice.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveStockDTO {
    @NotNull
    private String stockId;
    @NotNull
    private long orderAmount;
}
