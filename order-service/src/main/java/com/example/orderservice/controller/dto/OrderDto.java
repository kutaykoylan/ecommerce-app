package com.example.orderservice.controller.dto;

import com.example.orderservice.entity.OrderState;
import lombok.Data;

@Data
public class OrderDto {

    private final Long orderId;

    private final OrderState state;

    private final long price;

    private final String stockId;

    private final int reservedStockVersion;

    private final int orderAmount;

    private final String paymentAddress;

    private final float amount;

    private final String cardInformation;

    private final Long paymentId;

    private final String address;

    private final String description;

}
