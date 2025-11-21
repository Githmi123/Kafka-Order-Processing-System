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
		Order order1 = Order.newBuilder()
				.setOrderId("1001")
				.setProduct("Item1")
				.setPrice(199.99f)
				.build();

		Order order2 = Order.newBuilder()
				.setOrderId("1002")
				.setProduct("Item2")
				.setPrice(20f)
				.build();

		Order order3 = Order.newBuilder()
				.setOrderId("1003")
				.setProduct("Item3")
				.setPrice(50f)
				.build();

		Order order4 = Order.newBuilder()
				.setOrderId("1004")
				.setProduct("Item4")
				.setPrice(30f)
				.build();

		orderProducerService.sendOrder(order1);
		orderProducerService.sendOrder(order2);
		orderProducerService.sendOrder(order3);
		orderProducerService.sendOrder(order4);

		System.out.println("#### Test orders sent!");
	}

}
