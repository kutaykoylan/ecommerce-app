package com.example.orderservice.controller.dto;

import com.example.orderservice.entity.OrderState;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindOrderResponseDTO {

    private Long orderId;

    private OrderState state;

    private long price;

    private String stockId;

    private int reservedStockVersion;

    private int orderAmount;

    private String paymentAddress;

    private float amount;

    private String cardInformation;

    private Long paymentId;

    private String address;

    private String description;

}
