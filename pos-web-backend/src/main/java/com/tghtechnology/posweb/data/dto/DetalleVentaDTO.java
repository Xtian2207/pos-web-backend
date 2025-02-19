package com.tghtechnology.posweb.data.dto;

import lombok.Data;

@Data
public class DetalleVentaDTO {

    private Long idProducto;
    private Integer cantidad;
    private String nombreProducto;
    private Double subtotal;
    private Double precioProducto;
}
