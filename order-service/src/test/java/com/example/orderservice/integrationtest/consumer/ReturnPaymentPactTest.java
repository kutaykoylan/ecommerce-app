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
import com.example.orderservice.entity.Order;
import com.example.orderservice.kafka.consumer.OrderConsumerService;
import com.example.orderservice.kafka.dto.PaymentEventDTO;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.kafka.dto.ReturnPaymentEventDTO;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@PactFolder("../pact-message-consumer/target/pacts")
@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PactTestFor(providerName = "pactflow-return-payment-kafka", providerType = ProviderType.ASYNCH)
public class ReturnPaymentPactTest {
    @Autowired
    private OrderConsumerService orderConsumerService;
    @Autowired
    private OrderRepository orderRepository;

    private Long orderId;

    @BeforeEach
    public void before(){
        Order order = TestEntityBuilder.createOrderWithCancelledState();
        Order orderDb = orderRepository.save(order);
        orderId = orderDb.getId();
    }

    @Pact(consumer = "pactflow-return-payment-kafka")
    MessagePact returnPaymentValidMessage(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody();

        body.integerType("amount", 88)
                .integerType("orderId", orderId);


        return builder
                .expectsToReceive("return-payment")
                .withContent(body)
                .toPact();
    }

    @Pact(consumer = "pactflow-return-payment-kafka")
    MessagePact returnPaymentInvalidMessage(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody();

        return builder
                .expectsToReceive("return-payment")
                .withContent(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "returnPaymentValidMessage")
    void returnPaymentValidAttributesShouldBeConsumed(List<Message> messages) throws Exception {
        assertDoesNotThrow(() -> {
            String jsonStringMessage = messages.get(0).contentsAsString();
            ReturnPaymentEventDTO actualReturnPaymentEventDTO = orderConsumerService.returnPayment(jsonStringMessage);
            JSONObject jsonMessage = new JSONObject(jsonStringMessage);

            ReturnPaymentEventDTO expectedReturnPaymentEventDTO = new ReturnPaymentEventDTO(jsonMessage.getLong("orderId"), jsonMessage.getLong("amount"));

            assertEquals(expectedReturnPaymentEventDTO, actualReturnPaymentEventDTO, "Return payment parameters is not equal between message and event object");

        });
    }

    @Test
    @PactTestFor(pactMethod = "returnPaymentInvalidMessage")
    void returnPaymentInvalidAttributesShouldNotBeConsumed(List<Message> messages) throws Exception {
        assertThrows(ConstraintViolationException.class, () -> {
            String jsonStringMessage = messages.get(0).contentsAsString();
            ReturnPaymentEventDTO returnPaymentEventDTO = orderConsumerService.returnPayment(jsonStringMessage);
        });
    }

}
