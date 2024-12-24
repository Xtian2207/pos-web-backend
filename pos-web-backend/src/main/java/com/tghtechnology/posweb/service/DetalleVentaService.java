package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.DetalleVenta;

import java.util.List;

public interface DetalleVentaService {

    DetalleVenta registrarDetalleVenta(DetalleVenta detalleVenta);

    List<DetalleVenta> consultarDetallesVenta(Long idVenta);

    DetalleVenta actualizarDetalleVenta(Long idDetalle, Integer nuevaCantidad, Double nuevoPrecioUnitario);

    boolean eliminarDetalleVenta(Long idDetalle);

}
