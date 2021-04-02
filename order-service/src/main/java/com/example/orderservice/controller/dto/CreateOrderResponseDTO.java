package com.example.orderservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponseDTO {
    private Long id;
    private String stockId;
    private int orderAmount;
    private String description;
}
