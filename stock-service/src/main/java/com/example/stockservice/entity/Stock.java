package com.example.stockservice.entity;

import com.example.stockservice.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "STOCK")
public class Stock extends BaseEntity {

    private String stockName;

    private long remainingStock;

    @Enumerated(EnumType.STRING)
    private StockState state;

}
