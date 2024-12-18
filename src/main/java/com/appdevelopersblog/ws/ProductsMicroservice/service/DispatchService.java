package com.appdevelopersblog.ws.ProductsMicroservice.service;

import com.appdevelopersblog.ws.ProductsMicroservice.message.OrderCreated;
import com.appdevelopersblog.ws.ProductsMicroservice.message.OrderDispatched;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DispatchService {

    private static final String ORDER_DISPATCH_TOPIC = "order.dispatch";
    private final KafkaTemplate<String, Object> kafkaProducer;

    public void process(OrderCreated orderCreated) throws Exception {
        OrderDispatched orderDispatched = OrderDispatched.builder()
                .orderId(orderCreated.getOrderId())
                .build();

        kafkaProducer.send(ORDER_DISPATCH_TOPIC, orderDispatched).get();
    }

}
