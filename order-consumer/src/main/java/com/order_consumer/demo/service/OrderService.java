package com.order_consumer.demo.service;

import com.order_producer.demo.avro.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private double sum = 0.0;
    private int count = 0;

    public void processOrder(Order order) {
        count ++;
        sum += order.getPrice();
        double average = sum / count;

        System.out.println(
                "Received order " + order.getOrderId() +
                " | product = " + order.getProduct() +
                " | price = " + order.getPrice() +
                " | running average = " + average
        );
    }
}
