package com.example.paymentservice.integrationtest.consumer;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactFolder;
import au.com.dius.pact.core.model.messaging.Message;
import au.com.dius.pact.core.model.messaging.MessagePact;
import com.example.paymentservice.entity.PaymentInformation;
import com.example.paymentservice.kafka.consumer.PaymentConsumerService;
import com.example.paymentservice.kafka.consumer.dto.ProcessPaymentDTO;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@PactFolder("../pact-message-consumer/target/pacts")
@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PactTestFor(providerName = "pactflow-process-payment-kafka", providerType = ProviderType.ASYNCH)
public class ProcessPaymentPactTest {
    @Autowired
    private PaymentConsumerService paymentConsumerService;

    @Pact(consumer = "pactflow-process-payment-kafka")
    MessagePact processPaymentValidMessage(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody();
        PactDslJsonBody paymentInformation = new PactDslJsonBody();

        body.integerType("orderId", 1)
                .object("paymentInformation")
                .decimalType("amount", 50.0)
                .stringType("cardInformation", "1111222233334444")
                .stringType("paymentAddress", "paymentAddress")
                .closeObject();


        return builder
                .expectsToReceive("process-payment")
                .withContent(body)
                .toPact();
    }


    @Pact(consumer = "pactflow-process-payment-kafka")
    MessagePact processPaymentInvalidMessage(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody();
        PactDslJsonBody paymentInformation = new PactDslJsonBody();

        body.object("paymentInformation")
                .decimalType("amount", 50.0)
                .stringType("cardInformation", "1111222233334444")
                .stringType("paymentAddress", "paymentAddress")
                .closeObject();


        return builder
                .expectsToReceive("process-payment")
                .withContent(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "processPaymentValidMessage")
    void processPaymentValidAttributesShouldBeConsumed(List<Message> messages) throws Exception {
        assertDoesNotThrow(() -> {
            String jsonStringMessage = messages.get(0).contentsAsString();
            ProcessPaymentDTO processPaymentDTO = paymentConsumerService.processPayment(jsonStringMessage);
            JSONObject jsonMessage = new JSONObject(jsonStringMessage).getJSONObject("paymentInformation");

            PaymentInformation pI = new PaymentInformation(jsonMessage.getString("paymentAddress"), jsonMessage.getLong("amount"), jsonMessage.getString("cardInformation"));

            assertEquals(processPaymentDTO.getPaymentInformation(), pI, "Payment information parameter is not equal between message and event object");

        });
    }

    @Test
    @PactTestFor(pactMethod = "processPaymentInvalidMessage")
    void processPaymentInvalidAttributesShouldNotBeConsumed(List<Message> messages) throws Exception {
        assertThrows(ConstraintViolationException.class, () -> {
            String jsonStringMessage = messages.get(0).contentsAsString();
            ProcessPaymentDTO processPaymentDTO = paymentConsumerService.processPayment(jsonStringMessage);
        });
    }

}
