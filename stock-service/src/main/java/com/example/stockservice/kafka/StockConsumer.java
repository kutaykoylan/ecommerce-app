package com.example.stockservice.kafka;

import com.example.stockservice.kafka.dto.ReserveStockDTO;
import com.example.stockservice.service.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StockConsumer {

    @Autowired
    StockService stockService;

    @KafkaListener(topics = "reserve-stock",
            groupId ="reserve-stock-consumer")
    public void reserveStockListener(String message) {
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            ReserveStockDTO reserveStockDTO = mapper.readValue(message, ReserveStockDTO.class);
            stockService.decreaseStock(reserveStockDTO.getStockId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
