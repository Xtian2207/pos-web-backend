package com.tghtechnology.posweb.data.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tghtechnology.posweb.data.dto.VentaDTO;
import com.tghtechnology.posweb.data.entities.Cliente;
import com.tghtechnology.posweb.data.entities.TipoVenta;
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

        if (venta.getTipoDeVenta() != null) {
            ventaDTO.setTipoDeVenta(venta.getTipoDeVenta().name());
        }

        return ventaDTO;
    }

    public Venta toEntity(VentaDTO ventaDTO) {
        // Mapear el DTO de Venta a la entidad Venta
        Venta venta = modelMapper.map(ventaDTO, Venta.class);

        // Mapear el tipo de venta (String a enumerado)
        if (ventaDTO.getTipoDeVenta() != null) {
            venta.setTipoDeVenta(TipoVenta.valueOf(ventaDTO.getTipoDeVenta().toUpperCase())); // Convertir String a
                                                                                              // enumerado
        }

        if (ventaDTO.getCliente() != null && ventaDTO.getCliente().getIdCliente() != null) {
            Cliente cliente = new Cliente();
            cliente.setIdCliente(ventaDTO.getCliente().getIdCliente());
            venta.setCliente(cliente);
        }

        if (venta.getDetalles() != null) {
            venta.getDetalles().forEach(det -> det.setVenta(venta));
        }
        

        return venta;
    }

}
