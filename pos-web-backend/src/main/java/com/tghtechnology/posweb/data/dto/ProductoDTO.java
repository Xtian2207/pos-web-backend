package com.tghtechnology.posweb.data.dto;

import com.tghtechnology.posweb.data.entities.Categoria;
import com.tghtechnology.posweb.data.entities.EstadoProducto;

import lombok.Data;

@Data
public class ProductoDTO {

    private Long idProducto;
    private String nombreProducto;
    private String descripcion;
    private Double precio;
    private int cantidad;
    private EstadoProducto estado;
    private Categoria categoria;

}
