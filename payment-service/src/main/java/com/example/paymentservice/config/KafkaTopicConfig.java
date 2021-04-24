package com.example.paymentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaTopicConfig {
    @Bean
    public NewTopic processOrder() {
        return TopicBuilder.name("process-order").build();
    }
}
