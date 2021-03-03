package com.example.orderservice.entity;

import com.example.orderservice.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@javax.persistence.Entity(name = "ORDERS")
@SequenceGenerator(name = "idgen", sequenceName = "ORDER_SEQ")
public class Order extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private OrderState state;

    private long price;

    private String stockId;

    private int reservedStockVersion;

    private int orderAmount;

    private String paymentAddress;

    private float amount;

    private String cardInformation;

    private String paymentId;

    private String address;

    private String description;
}