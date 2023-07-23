package com.fasttrack.filapedidos.controllers;

import com.fasttrack.filapedidos.dtos.PedidoDto;
import com.fasttrack.filapedidos.models.PedidoModel;
import com.fasttrack.filapedidos.services.PedidosService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

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

}
