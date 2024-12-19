package com.appdevelopersblog.ws.ProductsMicroservice.handler;

import com.appdevelopersblog.ws.ProductsMicroservice.message.OrderCreated;
import com.appdevelopersblog.ws.ProductsMicroservice.service.DispatchService;
import com.appdevelopersblog.ws.ProductsMicroservice.util.TestEventData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void listen_Success() throws Exception {

        OrderCreated testEvent = TestEventData.buildOrderCreatedEvent(randomUUID(), randomUUID().toString());
        handler.listen(0, "", testEvent);

        verify(dispatchServiceMock, times(1)).process("", testEvent);
    }

    @Test
    void listen_ServiceThrowsException() throws Exception {

        OrderCreated testEvent = TestEventData.buildOrderCreatedEvent(randomUUID(), randomUUID().toString());
        doThrow(new RuntimeException("Service Failure")).when(dispatchServiceMock).process("", testEvent);
        handler.listen(0, "", testEvent);

        verify(dispatchServiceMock, times(1)).process("", testEvent);
    }
}