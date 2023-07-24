package com.fasttrack.filapedidos.dtos;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class OrderDto {

    @DecimalMin("0.0")
    private Float vr_pedido;
    @NotBlank
    @Size(max = 1)
    private String cd_status;
}
