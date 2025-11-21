# Kafka-Order-Processing-System - Big Data Assignment 1
---
### Reg. No: EG/2020/3943
### Name: Sundarasekara G.O.
---

This project implements a Kafka-based Order Processing system using Avro serialization, retry mechanisms, and a Dead Letter Queue (DLQ).

---

## Features
- Kafka producer and consumer using Avro serialization
- Real-time aggregation (running average of prices)
- Retry logic for temporary failures
- Dead Letter Queue (DLQ) for permanently failed messages

---

## What is Avro Serialization?
**Apache Avro** is a serialization framework used to efficiently encode structured data.  
- It ensures messages have a well-defined schema.  
- It guarantees compatibility between producer and consumer.  
- It reduces message size and improves performance compared to JSON.  

In this project, all the order messages follow the `Order` Avro schema:

| Field    | Type   | Description                     |
|----------|--------|---------------------------------|
| orderId  | string | Unique order identifier         |
| product  | string | Name of the purchased product  |
| price    | float  | Price of the product           |

---

## What is a Dead Letter Queue (DLQ)?

A **Dead Letter Queue** is a Kafka topic where messages that **cannot be processed successfully** are sent.  
- Temporary failures trigger retries via a retry topic (`orders-retry`).  
- Permanent failures (e.g., invalid data) are sent to the DLQ (`orders-dlq`) for later investigation.

---

## Prerequisites
- Docker & Docker Compose installed
  - Everything (Kafka broker, Schema Registry, producer, consumer) is included in the Docker setup.
  - No manual Kafka installation needed
 
---

## Technologies Used
- Java 17 & Spring Boot
- Apache Kafka
- Avro serialization
- Docker / Docker Compose

---

## Setup
1. Clone the repository
```
git clone https://github.com/Githmi123/Kafka-Order-Processing-System.git
```

2. Go in to the directory
```
cd Kafka-Order-Processing-System
```

4. Start all services
```
docker compose up --build -d
```

4. View consumer logs
```
docker logs order-consumer
```

---

## How it Works
The system consists of a Producer that sends orders and a Consumer that processes them with retry and DLQ logic.

### Topics Used
| Topic           | Purpose                                                                 |
|-----------------|-------------------------------------------------------------------------|
| `orders`        | Main topic where new orders are published by the producer               |
| `orders-retry`  | Retry topic for messages that fail temporarily                          |
| `orders-dlq`    | Dead Letter Queue for permanently failed messages                        |

### Producer
- The producer sends orders to the 'orders' topic using Avro serialization.
- The test orders include:
    - three normal orders
    - a simulated failure order (product = `"FAIL"`)

```java
orderProducerService.sendOrder(order1);
orderProducerService.sendOrder(order2);
orderProducerService.sendOrder(failOrder);
orderProducerService.sendOrder(order4);
```

### Consumer
- The consumer listens to the 'orders' topic and processes each order:
  1. Calculates the running average of order prices.
  2. Processes valid orders successfully.
  3. If a temporary failure occurs, the message is sent to the retry topic ('orders-retry') and are retried automatically.
  4. If the message still fails after the configured retry attempts, it is sent to the Dead Letter Queue ('orders-dlq') for later inspection.
 
**Simulated failure**
When product == "FAIL" -> Throws a RuntimeException intentionally

**Retry Logic**
```java
@RetryableTopic(
        attempts = "3",
        backoff = @Backoff(delay = 2000), // 2 seconds delay
        dltTopicSuffix = "-dlq",
        include = Exception.class
)
```

After 3 total attempts, if it still fails, message is sent to DLQ.
After each failure, Spring Kafka waits 2 seconds before retrying.

## Cleanup
To stop the system:
```
docker compose down
```


