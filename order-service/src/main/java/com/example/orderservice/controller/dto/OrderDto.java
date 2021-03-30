package com.example.orderservice.controller.dto;

import com.example.orderservice.entity.OrderState;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {

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
