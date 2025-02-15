package com.tghtechnology.posweb.data.dto;

import com.tghtechnology.posweb.data.entities.Categoria;
import com.tghtechnology.posweb.data.entities.EstadoProducto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductoDTO {

    private Long idProducto;
    
    @NotBlank(message = "El nombre del producto no puede estar vacío")    
    @Size(max = 100, message = "El nombre del producto no debe exceder los 100 caracteres")
    private String nombreProducto;

    @Size(max = 255, message = "La descripción no debe exceder los 255 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private Double precio;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;

    @NotNull(message = "El estado del producto es obligatorio")
    private EstadoProducto estado;

    private Categoria categoria;

    private String imagenUrl;
}
