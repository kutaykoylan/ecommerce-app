package com.example.orderservice.kafka.producer;

import com.example.orderservice.kafka.dto.ProcessPaymentDTO;
import com.example.orderservice.kafka.dto.ReleaseStockDTO;
import com.example.orderservice.kafka.dto.ReserveStockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendReserveStockEvent(ReserveStockDTO reserveStockDTO) {
        kafkaTemplate.send("reserve-stock", reserveStockDTO);
    }

    public void sendReleaseStockEvent(ReleaseStockDTO releaseStockDTO) {
        kafkaTemplate.send("release-stock", releaseStockDTO);
    }

    public void sendProcessPaymentEvent(ProcessPaymentDTO processPaymentDTO) {
        kafkaTemplate.send("process-payment", processPaymentDTO);
    }

}
