package com.example.orderservice.integrationtest.consumer;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactFolder;
import au.com.dius.pact.core.model.messaging.Message;
import au.com.dius.pact.core.model.messaging.MessagePact;
import com.example.orderservice.common.TestEntityBuilder;
import com.example.orderservice.kafka.consumer.OrderConsumerService;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.orderservice.kafka.dto.PaymentEventDTO;
import com.example.orderservice.kafka.dto.ReturnPaymentEventDTO;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@PactFolder("src/test/resources/pact-message-consumer/target/pacts")
@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PactTestFor(providerName = "pactflow-success-payment-kafka", providerType = ProviderType.ASYNCH)
public class SuccessPaymentPactTest {
    @Autowired
    private OrderConsumerService orderConsumerService;
    @Autowired
    private OrderRepository orderRepository;

    private Long orderId;

    @BeforeEach
    public void before(){
        Order order = TestEntityBuilder.createOrderWithPaymentReadyState();
        Order orderDb = orderRepository.save(order);
        orderId = orderDb.getId();
    }

    @Pact(consumer = "pactflow-success-payment-kafka")
    MessagePact successPaymentValidMessage(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody();

        body.integerType("amount", 88)
                .integerType("orderId", orderId)
                .stringType("paymentAddress", "Test")
                .stringType("cardInformation", "1111222233334444");


        return builder
                .expectsToReceive("success-payment")
                .withContent(body)
                .toPact();
    }

    @Pact(consumer = "pactflow-success-payment-kafka")
    MessagePact successPaymentInvalidMessage(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody();

        return builder
                .expectsToReceive("success-payment")
                .withContent(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "successPaymentValidMessage")
    void successPaymentValidAttributesShouldBeConsumed(List<Message> messages) throws Exception {
        assertDoesNotThrow(() -> {
            String jsonStringMessage = messages.get(0).contentsAsString();
            PaymentEventDTO actualPaymentEventDTO = orderConsumerService.successPayment(jsonStringMessage);
            JSONObject jsonMessage = new JSONObject(jsonStringMessage);

            PaymentEventDTO expectedPaymentEventDTO = new PaymentEventDTO(jsonMessage.getLong("amount"), jsonMessage.getLong("orderId"), jsonMessage.getString("paymentAddress"), jsonMessage.getString("cardInformation"));

            assertEquals(expectedPaymentEventDTO, actualPaymentEventDTO, "Reserve stock parameters is not equal between message and event object");

        });
    }

    @Test
    @PactTestFor(pactMethod = "successPaymentInvalidMessage")
    void successPaymentInvalidAttributesShouldNotBeConsumed(List<Message> messages) throws Exception {
        assertThrows(ConstraintViolationException.class, () -> {
            String jsonStringMessage = messages.get(0).contentsAsString();
            PaymentEventDTO paymentEventDTO = orderConsumerService.successPayment(jsonStringMessage);
        });
    }

}
