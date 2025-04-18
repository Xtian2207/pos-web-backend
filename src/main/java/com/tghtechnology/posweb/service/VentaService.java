package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.dto.VentaDTO;
import com.tghtechnology.posweb.exceptions.ResourceNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VentaService {

    VentaDTO registrarVenta(VentaDTO ventaDTO) throws Exception;

    List<VentaDTO> listarVentas();

    Optional<VentaDTO> obtenerVentaPorId(Long idVenta);

    VentaDTO actualizarVenta(Long idVenta, VentaDTO ventaDTO);

    void eliminarVenta(Long idVenta);

    List<VentaDTO> obtenerVentasPorUsuario(Long usuarioId);

    List<VentaDTO> obtenerVentasPorFecha(Date fechaInicio, Date fechaFin);

    List<VentaDTO> obtenerVentasPorAnio(int anio);

    VentaDTO agregarProductoPorCodigoBarras(VentaDTO ventaDTO, String codigoBarras) throws ResourceNotFoundException;
}
