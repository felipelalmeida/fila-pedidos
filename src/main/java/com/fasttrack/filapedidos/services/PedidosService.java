package com.fasttrack.filapedidos.services;

import com.fasttrack.filapedidos.models.PedidoModel;
import com.fasttrack.filapedidos.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidosService {

    @Autowired
    PedidoRepository pedidoRepository;

    public PedidoModel save(PedidoModel pedidoModel) {
        return pedidoRepository.save(pedidoModel);
    }
}
