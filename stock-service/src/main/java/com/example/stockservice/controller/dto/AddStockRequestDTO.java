package com.example.stockservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStockRequestDTO {
    private long stockToAdd;
    private long stockId;
}
