package com.fasttrack.filapedidos.listerners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderListener {

    @RabbitListener(queues = "orders.order-created")
    public void onOrderCreated(String message) {
        log.info("RabbitMQ, order created: " + message);
    }

    @RabbitListener(queues = "orders.order-updated")
    public void onOrderUpdated(String message) {
        log.info("RabbitMQ, order updated: " + message);
    }
}
