package com.fasttrack.filapedidos.services;

import com.fasttrack.filapedidos.models.OrderModel;
import com.fasttrack.filapedidos.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Transactional
    public OrderModel save(OrderModel orderModel) {
        return orderRepository.save(orderModel);
    }

    public Page<OrderModel> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Optional<OrderModel> findById(UUID id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public void delete(OrderModel orderModel) {
        orderRepository.delete(orderModel);
    }
}
