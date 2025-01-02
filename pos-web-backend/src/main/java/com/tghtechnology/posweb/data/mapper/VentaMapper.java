package com.tghtechnology.posweb.data.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tghtechnology.posweb.data.dto.VentaDTO;
import com.tghtechnology.posweb.data.entities.Venta;

@Component
public class VentaMapper {

    @Autowired
    private ModelMapper modelMapper;

    public VentaDTO toDTO(Venta venta) {
        return modelMapper.map(venta, VentaDTO.class);
    }


    public Venta toEntity(VentaDTO ventaDTO) {
        // Mapeamos el DTO de Venta a la entidad Venta
        return modelMapper.map(ventaDTO, Venta.class);
    }

}
