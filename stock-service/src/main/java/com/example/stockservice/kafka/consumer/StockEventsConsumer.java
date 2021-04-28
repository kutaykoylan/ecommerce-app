package com.example.stockservice.kafka.consumer;

import com.example.stockservice.common.exception.StockException;
import com.example.stockservice.controller.dto.AddStockRequestDTO;
import com.example.stockservice.kafka.dto.ReserveStockDTO;
import com.example.stockservice.service.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;


@Component
@RequiredArgsConstructor
@Log4j2
public class StockEventsConsumer {
    private final StockService stockService;

    @KafkaListener(topics = "reserve-stock",
            groupId ="stock-service")
    public void reserveStock(String message) throws JsonProcessingException, StockException {
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        ReserveStockDTO reserveStockDTO = mapper.readValue(message, ReserveStockDTO.class);
        stockService.decreaseStock(reserveStockDTO);
    }

    @KafkaListener(topics = "release-stock",
            groupId ="stock-service")
    public void releaseStock(String message) throws JsonProcessingException, StockException {
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        ReserveStockDTO reserveStockDTO = mapper.readValue(message, ReserveStockDTO.class);
        AddStockRequestDTO addStockRequestDTO = new AddStockRequestDTO(reserveStockDTO.getOrderAmount(), Long.parseLong(reserveStockDTO.getStockId()));
        stockService.addStock(addStockRequestDTO);
    }

    @ExceptionHandler({StockException.class})
    public void handleException(Exception e, HttpServletRequest request) {
        String error = e.getMessage();
        log.error(request.getRequestURI(),error);
    }
}
