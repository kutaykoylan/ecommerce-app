package com.example.orderservice.common;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderState;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestEntityBuilder {
    public Order createOrderWithPaymentReadyState(){
        Order order = new Order();
        order.setState(OrderState.PAYMENT_READY);
        return order;
    }
    public Order createOrderWithCancelledState(){
        Order order = new Order();
        order.setState(OrderState.CANCELLED);
        return order;
    }
}
