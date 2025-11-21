package com.order_consumer.demo.consumer;

import com.order_consumer.demo.service.OrderService;
import com.order_producer.demo.avro.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    private final OrderService orderService;

    public OrderConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 2000), // 2 seconds delay
            dltTopicSuffix = "-dlq",
            include = Exception.class
    )
    @KafkaListener(topics = "orders", groupId = "order-consumer-group")
    public void consumeOrder(Order order) {
        System.out.println("\n#### Received order: " + order);
        System.out.println("#### DEBUG: product='" + order.getProduct() + "'");

        //Simulate failure
        if ("FAIL".equals(order.getProduct().toString())) {
            System.out.println("#### Simulated Failure");
            throw new RuntimeException("@@@ Simulated Failure @@@");
        }
        orderService.processOrder(order);
        System.out.println("#### Order processed successfully on attempt");
    }
}
