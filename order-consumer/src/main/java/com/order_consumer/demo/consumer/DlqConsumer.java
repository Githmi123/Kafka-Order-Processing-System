package com.order_consumer.demo.consumer;

import com.order_producer.demo.avro.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DlqConsumer {
    @KafkaListener(topics = "orders-dlq", groupId = "order-consumer-group-dlq")
    public void consumeDlq(Order order) {
        System.out.println("#### DLQ message received: " + order);
    }
}
