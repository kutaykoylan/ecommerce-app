package com.example.paymentservice.entity;

import com.example.paymentservice.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "PAYMENT")
public class Payment extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private PaymentState state;

    private String paymentAddress;

    private String orderId;

    private float amount;

    private String cardInformation;

}
