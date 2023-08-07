package com.fasttrack.filapedidos.listerners;

import com.fasttrack.filapedidos.dtos.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderListener {

    @RabbitListener(queues = "orders.order-created")
    public void onOrderCreated(OrderDto orderDto) {
        log.info("RabbitMQ, order created -> value: " + orderDto.getVr_pedido() + ", status: " + orderDto.getCd_status());
    }

    @RabbitListener(queues = "orders.order-updated")
    public void onOrderUpdated(OrderDto orderDto) {
        log.info("RabbitMQ, order updated -> value: " + orderDto.getVr_pedido() + ", status: " + orderDto.getCd_status());
    }
}
