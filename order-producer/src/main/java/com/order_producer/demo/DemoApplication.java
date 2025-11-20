package com.order_producer.demo;

import com.order_producer.demo.avro.Order;
import com.order_producer.demo.service.OrderProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	private final OrderProducerService orderProducerService;

	public DemoApplication(OrderProducerService orderProducerService) {
		this.orderProducerService = orderProducerService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Order order = Order.newBuilder()
				.setOrderId("1001")
				.setProduct("Item1")
				.setPrice(199.99f)
				.build();

		orderProducerService.sendOrder(order);
	}

}
