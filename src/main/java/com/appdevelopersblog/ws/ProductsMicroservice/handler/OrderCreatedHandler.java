package com.appdevelopersblog.ws.ProductsMicroservice.handler;

import com.appdevelopersblog.ws.ProductsMicroservice.message.OrderCreated;
import com.appdevelopersblog.ws.ProductsMicroservice.service.DispatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(
        id = "orderConsumerClient",
        topics = "order.created",
        groupId = "dispatch.order.created.consumer"
)
public class OrderCreatedHandler {

    private final DispatchService dispatchService;

    @KafkaHandler
    public void listen(@Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition, @Header(KafkaHeaders.RECEIVED_KEY) String key, @Payload OrderCreated payload) {

        log.info("Received message: partition: " + partition + ", key: " + key + ", payload: " + payload);

        try {
            dispatchService.process(key, payload);
        } catch (Exception e) {
            log.error("Processing failure", e);
        }
    }

}
