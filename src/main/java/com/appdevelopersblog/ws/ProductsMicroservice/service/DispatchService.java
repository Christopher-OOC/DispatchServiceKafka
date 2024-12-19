package com.appdevelopersblog.ws.ProductsMicroservice.service;

import com.appdevelopersblog.ws.ProductsMicroservice.message.OrderCreated;
import com.appdevelopersblog.ws.ProductsMicroservice.message.OrderDispatched;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class DispatchService {

    private static final String ORDER_DISPATCH_TOPIC = "order.dispatch";
    private static final String DISPATCH_TRACKING_TOPIC = "order.dispatch";
    private final KafkaTemplate<String, Object> kafkaProducer;
    private static final UUID APPLICATION_ID = randomUUID();

    public void process(OrderCreated orderCreated) throws Exception {

        OrderDispatched dispatchPreparing = OrderDispatched.builder()
                .orderId(orderCreated.getOrderId())
                .build();

        kafkaProducer.send(DISPATCH_TRACKING_TOPIC, dispatchPreparing).get();

        OrderDispatched orderDispatched = OrderDispatched.builder()
                .orderId(orderCreated.getOrderId())
                .build();

        kafkaProducer.send(ORDER_DISPATCH_TOPIC, orderDispatched).get();
    }
}
