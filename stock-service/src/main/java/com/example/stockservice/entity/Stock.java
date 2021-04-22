package com.example.stockservice.entity;

import com.example.stockservice.common.data.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "STOCK")
public class Stock extends BaseEntity {

    private String stockName;

    private long remainingStock;

    private long price;

    @Enumerated(EnumType.STRING)
    private StockState state;

}
