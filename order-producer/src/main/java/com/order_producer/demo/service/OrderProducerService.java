package com.order_producer.demo.service;

import com.order_producer.demo.avro.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducerService {
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final String TOPIC = "orders";

    public OrderProducerService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(Order order) {
        kafkaTemplate.send(TOPIC, (String) order.getOrderId(), order);
        System.out.println("Sent order: " + order.getOrderId());
    }
}
