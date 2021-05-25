package com.example.stockservice.integrationtest.consumer;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactFolder;
import au.com.dius.pact.core.model.messaging.Message;
import au.com.dius.pact.core.model.messaging.MessagePact;
import com.example.stockservice.kafka.consumer.StockEventsConsumer;
import com.example.stockservice.kafka.dto.ReserveStockDTO;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@PactFolder("src/test/resources/pact-message-consumer/target/pacts")
@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@PactTestFor(providerName = "pactflow-reserve-stock-kafka", providerType = ProviderType.ASYNCH)
public class ReserveStockPactTest {

    @Autowired
    private StockEventsConsumer stockEventsConsumer;

    @Pact(consumer = "pactflow-release-stock-kafka")
    MessagePact reserveStockValidMessage(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody();

        body.stringType("stockId", "34")
                .integerType("orderAmount",20);


        return builder
                .expectsToReceive("reserve-stock")
                .withContent(body)
                .toPact();
    }
    @Pact(consumer = "pactflow-release-stock-kafka")
    MessagePact reserveStockInvalidMessage(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody();

        return builder
                .expectsToReceive("reserve-stock")
                .withContent(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "reserveStockValidMessage")
    void reserveStockValidAttributesShouldBeConsumed(List<Message> messages) throws Exception {
        assertDoesNotThrow(() -> {
            String jsonStringMessage = messages.get(0).contentsAsString();
            ReserveStockDTO actualReserveStockDTO = stockEventsConsumer.reserveStock(jsonStringMessage);
            JSONObject jsonMessage = new JSONObject(jsonStringMessage);

            ReserveStockDTO expectedReserveStockDTO = new ReserveStockDTO(jsonMessage.getString("stockId"), jsonMessage.getLong("orderAmount"));

            assertEquals(expectedReserveStockDTO, actualReserveStockDTO, "Reserve stock parameters is not equal between message and event object");

        });
    }

    @Test
    @PactTestFor(pactMethod = "reserveStockInvalidMessage")
    void reserveStockInvalidAttributesShouldNotBeConsumed(List<Message> messages) throws Exception {
        assertThrows(ConstraintViolationException.class, () -> {
            String jsonStringMessage = messages.get(0).contentsAsString();
            ReserveStockDTO reserveStockDTO = stockEventsConsumer.reserveStock(jsonStringMessage);
        });
    }
}
