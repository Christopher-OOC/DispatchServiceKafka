package com.appdevelopersblog.ws.ProductsMicroservice.service;

import com.appdevelopersblog.ws.ProductsMicroservice.message.OrderCreated;
import com.appdevelopersblog.ws.ProductsMicroservice.message.OrderDispatched;
import com.appdevelopersblog.ws.ProductsMicroservice.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;

import static java.util.UUID.randomUUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DispatchServiceTest {

    private DispatchService service;
    private KafkaTemplate<String, Object> kafkaProducerMock;

    @BeforeEach
    void setUp() {
        kafkaProducerMock = mock(KafkaTemplate.class);
        service = new DispatchService(kafkaProducerMock);
    }

    @Test
    void process() throws Exception {
        when(kafkaProducerMock.send(anyString(), any(OrderDispatched.class))).thenReturn(mock(CompletableFuture.class));

        OrderCreated testEvent = TestEventData.buildOrderCreatedEvent(randomUUID(), randomUUID().toString());
        service.process(testEvent);

        verify(kafkaProducerMock, times(1)).send(eq("order.dispatch"), any(OrderDispatched.class));
    }

    @Test
    void process_ProducerThrowsException() throws Exception {
        doThrow(new RuntimeException("Producer Failure")).when(kafkaProducerMock).send(eq("order.dispatched"), any(OrderDispatched.class)).get();

        OrderCreated testEvent = TestEventData.buildOrderCreatedEvent(randomUUID(), randomUUID().toString());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.process(testEvent));
        verify(kafkaProducerMock, times(1)).send(eq("order.dispatch"), any(OrderDispatched.class));
        assertThat(exception.getMessage(), equalTo("Producer Failure"));
    }
}