package com.fasttrack.filapedidos.controllers;

import com.fasttrack.filapedidos.dtos.OrderDto;
import com.fasttrack.filapedidos.models.OrderModel;
import com.fasttrack.filapedidos.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.order-created}")
    private String queueOrderCreated;

    @Value("${rabbitmq.queue.order-updated}")
    private String queueOrderUpdated;

    @PostMapping
    public ResponseEntity<OrderModel> saveOrder(@RequestBody @Valid OrderDto orderDto) {
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderDto, orderModel);
        orderModel.setDt_criacao(LocalDateTime.now());
        orderModel.setDt_atualizacao(LocalDateTime.now());
        log.info("Order created successfully");
        rabbitTemplate.convertAndSend(queueOrderCreated, orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(orderModel));
    }

    @GetMapping
    public ResponseEntity<Page<OrderModel>> getAllOrders(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        log.info("Looking for all orders");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneOrder(@PathVariable(value = "id") UUID id) {
        Optional<OrderModel> orderModelOptional = orderService.findById(id);
        if(!orderModelOptional.isPresent()) {
            log.warn("Order not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found!");
        }
        log.info("Looking for order with id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(orderModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable(value = "id") UUID id,
                                               @RequestBody @Valid OrderDto orderDto) {
        Optional<OrderModel> orderModelOptional = orderService.findById(id);
        if(!orderModelOptional.isPresent()) {
            log.warn("Order not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found!");
        }
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderDto, orderModel);
        orderModel.setDt_criacao(orderModelOptional.get().getDt_criacao());
        orderModel.setCd_pedido(orderModelOptional.get().getCd_pedido());
        orderModel.setDt_atualizacao(LocalDateTime.now());
        log.info("Order updated");
        rabbitTemplate.convertAndSend(queueOrderUpdated, orderDto);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.save(orderModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable(value = "id") UUID id) {
        Optional<OrderModel> orderModelOptional = orderService.findById(id);
        if(!orderModelOptional.isPresent()) {
            log.warn("Order not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found!");
        }
        orderService.delete(orderModelOptional.get());
        log.info("Order deleted");
        return ResponseEntity.status(HttpStatus.OK).body("Order deleted successfully!");
    }

}
