package com.fasttrack.filapedidos.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "PEDIDOS")
@Setter
@Getter
public class PedidoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID cd_pedido;
    @Column(nullable = false)
    private Float vr_pedido;
    @Column(nullable = false, length = 1)
    private String cd_status;
    @Column(nullable = false)
    private LocalDateTime dt_criacao;
    @Column(nullable = false)
    private LocalDateTime dt_atualizacao;


}
