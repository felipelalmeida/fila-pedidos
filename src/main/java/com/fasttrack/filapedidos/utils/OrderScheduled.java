package com.fasttrack.filapedidos.utils;

import com.fasttrack.filapedidos.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderScheduled {

    @Autowired
    OrderRepository orderRepository;

    private final long second = 1000;
    private final long minute = second * 60;
    private final long hour = minute * 60;

//    @Scheduled(initialDelay = second * 10, fixedDelay = second * 15)
    @Scheduled(cron = "0 43 13 * * *")
    public void getOrdersWithStatus() {
        Integer ordersCount = orderRepository.countByCdStatus("3");
        log.info("Orders with status incomplete: " + ordersCount);
        if (ordersCount > 0) {
            log.info("Orders with status incomplete updated to abandoned: " + orderRepository.updateStatus3To4());
        }
    }

}