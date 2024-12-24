package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.DetalleVenta;
import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.entities.Venta;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VentaService {

    Venta registrarVenta(Venta venta);

    List<Venta> listarVentas();

    Optional<Venta> obtenerVentaPorId(Long idVenta);

    Venta actualizarVenta(Long idVenta, Venta ventaActualizada);

    void eliminarVenta(Long idVenta);

    List<Venta> obtenerVentasPorUsuario(Long usuarioId);

    List<Venta> obtenerVentasPorFecha(Date fechaInicio, Date fechaFin);

}
