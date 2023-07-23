package com.fasttrack.filapedidos.services;

import com.fasttrack.filapedidos.models.PedidoModel;
import com.fasttrack.filapedidos.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidosService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Transactional
    public PedidoModel save(PedidoModel pedidoModel) {
        return pedidoRepository.save(pedidoModel);
    }

    public Page<PedidoModel> findAll(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    public Optional<PedidoModel> findById(UUID id) {
        return pedidoRepository.findById(id);
    }
}
