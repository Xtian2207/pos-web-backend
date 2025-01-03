package com.tghtechnology.posweb.data.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tghtechnology.posweb.data.dto.ProductoDTO;
import com.tghtechnology.posweb.data.entities.Producto;

@Component
public class ProductoMapper {

    @Autowired
    private ModelMapper modelMapper;

    // Convierte ProductoDTO a Producto (para guardar en la base de datos)
    public Producto toEntity(ProductoDTO productoDTO) {
        return modelMapper.map(productoDTO, Producto.class);
    }

    // Actualiza un Producto existente con valores de un ProductoDTO
    public void toEntity(ProductoDTO productoDTO, Producto productoExistente) {
        modelMapper.map(productoDTO, productoExistente);
    }

    // Convierte Producto a ProductoDTO (para devolver a la capa de presentación)
    public ProductoDTO toDTO(Producto producto) {
        return modelMapper.map(producto, ProductoDTO.class);
    }
}
