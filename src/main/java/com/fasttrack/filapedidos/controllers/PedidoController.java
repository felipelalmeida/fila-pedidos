package com.fasttrack.filapedidos.controllers;

import com.fasttrack.filapedidos.dtos.PedidoDto;
import com.fasttrack.filapedidos.models.PedidoModel;
import com.fasttrack.filapedidos.services.PedidosService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    PedidosService pedidosService;

    @PostMapping
    public ResponseEntity<PedidoModel> savePedido(@RequestBody @Valid PedidoDto pedidoDto) {
        PedidoModel pedidoModel = new PedidoModel();
        BeanUtils.copyProperties(pedidoDto, pedidoModel);
        pedidoModel.setDt_criacao(LocalDateTime.now());
        pedidoModel.setDt_atualizacao(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidosService.save(pedidoModel));
    }

    @GetMapping
    public ResponseEntity<Page<PedidoModel>> getAllPedidos(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(pedidosService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOnePedido(@PathVariable(value = "id") UUID id) {
        Optional<PedidoModel> pedidoModelOptional = pedidosService.findById(id);
        if(!pedidoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pedidoModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePedido(@PathVariable(value = "id") UUID id,
                                               @RequestBody @Valid PedidoDto pedidoDto) {
        Optional<PedidoModel> pedidoModelOptional = pedidosService.findById(id);
        if(!pedidoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado!");
        }
        PedidoModel pedidoModel = new PedidoModel();
        BeanUtils.copyProperties(pedidoDto, pedidoModel);
        pedidoModel.setDt_criacao(pedidoModelOptional.get().getDt_criacao());
        pedidoModel.setCd_pedido(pedidoModelOptional.get().getCd_pedido());
        pedidoModel.setDt_atualizacao(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(pedidosService.save(pedidoModel));
    }

}
