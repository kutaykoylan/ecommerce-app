package com.example.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic processPayment(){
        return TopicBuilder.name("process-payment").build();
    }

    @Bean
    public NewTopic stockReserved() {
        return TopicBuilder.name("reserve-stock").build();
    }

    @Bean
    public NewTopic stockReleased() {
        return TopicBuilder.name("release-stock").build();
    }

    @Bean
    public NewTopic paymentSuccess() {
        return TopicBuilder.name("success-payment").build();
    }

    @Bean
    public NewTopic paymentFail() { return TopicBuilder.name("fail-payment").build(); }



}
