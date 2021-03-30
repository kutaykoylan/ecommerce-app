package com.example.orderservice.entity;

import com.example.orderservice.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
@SequenceGenerator(name = "idgen", sequenceName = "ORDER_SEQ")
public class Order extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column
    private OrderState state;

    @Column
    private long price;

    @Column
    private String stockId;

    @Column
    private int reservedStockVersion;

    @Column
    private int orderAmount;

    @Column
    private String paymentAddress;

    @Column
    private float amount;

    @Column
    private String cardInformation;

    @Column
    private String paymentId;

    @Column
    private String address;

    @Column
    private String description;
}