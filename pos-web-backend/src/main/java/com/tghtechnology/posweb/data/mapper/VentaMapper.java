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
        VentaDTO ventaDTO = modelMapper.map(venta, VentaDTO.class);

        // Mapear el nombre del usuario
        if (venta.getUsuario() != null) {
            ventaDTO.setNombreUsuario(venta.getUsuario().getNombre());
        }

        // Mapear el nombre del cliente
        if (venta.getCliente() != null) {
            ventaDTO.setNombreCliente(venta.getCliente().getNombre());
        }

        return ventaDTO;
    }


    public Venta toEntity(VentaDTO ventaDTO) {
        // Mapeamos el DTO de Venta a la entidad Venta
        return modelMapper.map(ventaDTO, Venta.class);
    }

}
