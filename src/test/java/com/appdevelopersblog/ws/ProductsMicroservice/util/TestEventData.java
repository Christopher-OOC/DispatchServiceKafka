package com.appdevelopersblog.ws.ProductsMicroservice.util;

import com.appdevelopersblog.ws.ProductsMicroservice.message.OrderCreated;

import java.util.UUID;

public class TestEventData {

    public static OrderCreated buildOrderCreatedEvent(UUID orderId, String item) {

        return OrderCreated.builder()
                .orderId(orderId)
                .item(item)
                .build();
    }

}
