package com.order_consumer.demo.consumer;

import com.order_consumer.demo.service.OrderService;
import com.order_producer.demo.avro.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    private final OrderService orderService;

    public OrderConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "orders", groupId = "order-consumer-group")
    public void consumeOrder(Order order) {
        orderService.processOrder(order);
    }
}
