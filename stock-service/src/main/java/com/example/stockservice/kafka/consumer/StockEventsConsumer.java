package com.example.stockservice.kafka.consumer;

import com.example.stockservice.controller.dto.AddStockRequestDTO;
import com.example.stockservice.kafka.dto.ReserveStockDTO;
import com.example.stockservice.service.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StockEventsConsumer {
    @Autowired
    StockService stockService;

    @KafkaListener(topics = "reserve-stock",
            groupId ="stock-service")
    public void reserveStock(String message) throws JsonProcessingException {
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        ReserveStockDTO reserveStockDTO = mapper.readValue(message, ReserveStockDTO.class);
        stockService.decreaseStock(reserveStockDTO);
    }

    @KafkaListener(topics = "release-stock",
            groupId ="stock-service")
    public void releaseStock(String message) throws JsonProcessingException {
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        ReserveStockDTO reserveStockDTO = mapper.readValue(message, ReserveStockDTO.class);
        AddStockRequestDTO addStockRequestDTO = new AddStockRequestDTO(reserveStockDTO.getOrderAmount(), Long.parseLong(reserveStockDTO.getStockId()));
        stockService.addStock(addStockRequestDTO);
    }
}
