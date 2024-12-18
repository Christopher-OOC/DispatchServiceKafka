package com.appdevelopersblog.ws.ProductsMicroservice.handler;

import com.appdevelopersblog.ws.ProductsMicroservice.message.OrderCreated;
import com.appdevelopersblog.ws.ProductsMicroservice.service.DispatchService;
import com.appdevelopersblog.ws.ProductsMicroservice.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

class OrderCreatedHandlerTest {

    private OrderCreatedHandler handler;
    private DispatchService dispatchServiceMock;

    @BeforeEach
    void setUp() {
        dispatchServiceMock = mock(DispatchService.class);
        handler = new OrderCreatedHandler(dispatchServiceMock);
    }


    @Test
    void listen() throws Exception {

        OrderCreated testEvent = TestEventData.buildOrderCreatedEvent(randomUUID(), randomUUID().toString());
        handler.listen(testEvent);

        verify(dispatchServiceMock, times(1)).process(testEvent);
    }
}