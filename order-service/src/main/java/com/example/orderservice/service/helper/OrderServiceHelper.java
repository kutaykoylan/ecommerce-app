package com.example.orderservice.service.helper;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderState;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceHelper {
    public boolean isOrderStateInitial(Order order) {
        return order.getState() != OrderState.INITIAL;
    }
    public boolean isOrderStatePaid(Order order) {
        return order.getState() != OrderState.PAID;
    }
}
