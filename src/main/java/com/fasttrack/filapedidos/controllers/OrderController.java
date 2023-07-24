package com.fasttrack.filapedidos.controllers;

import com.fasttrack.filapedidos.dtos.OrderDto;
import com.fasttrack.filapedidos.models.OrderModel;
import com.fasttrack.filapedidos.services.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderModel> savePedido(@RequestBody @Valid OrderDto orderDto) {
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderDto, orderModel);
        orderModel.setDt_criacao(LocalDateTime.now());
        orderModel.setDt_atualizacao(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(orderModel));
    }

    @GetMapping
    public ResponseEntity<Page<OrderModel>> getAllPedidos(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOnePedido(@PathVariable(value = "id") UUID id) {
        Optional<OrderModel> pedidoModelOptional = orderService.findById(id);
        if(!pedidoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pedidoModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePedido(@PathVariable(value = "id") UUID id,
                                               @RequestBody @Valid OrderDto orderDto) {
        Optional<OrderModel> pedidoModelOptional = orderService.findById(id);
        if(!pedidoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado!");
        }
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderDto, orderModel);
        orderModel.setDt_criacao(pedidoModelOptional.get().getDt_criacao());
        orderModel.setCd_pedido(pedidoModelOptional.get().getCd_pedido());
        orderModel.setDt_atualizacao(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(orderService.save(orderModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePedido(@PathVariable(value = "id") UUID id) {
        Optional<OrderModel> pedidoModelOptional = orderService.findById(id);
        if(!pedidoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado!");
        }
        orderService.delete(pedidoModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Pedido deletado com sucesso!");
    }
}
