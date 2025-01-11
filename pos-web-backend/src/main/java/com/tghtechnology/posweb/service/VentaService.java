package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.dto.ClienteDTO;
import com.tghtechnology.posweb.data.dto.VentaDTO;
import com.tghtechnology.posweb.data.entities.Cliente;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VentaService {

    VentaDTO registrarVenta(VentaDTO ventaDTO);

    List<VentaDTO> listarVentas();

    Optional<VentaDTO> obtenerVentaPorId(Long idVenta);

    VentaDTO actualizarVenta(Long idVenta, VentaDTO ventaDTO);

    void eliminarVenta(Long idVenta);

    List<VentaDTO> obtenerVentasPorUsuario(Long usuarioId);

    List<VentaDTO> obtenerVentasPorFecha(Date fechaInicio, Date fechaFin);


}
