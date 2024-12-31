package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.entities.DetalleVenta;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.entities.Venta;
import com.tghtechnology.posweb.data.repository.DetalleVentaRepository;
import com.tghtechnology.posweb.data.repository.ProductoRepository;
import com.tghtechnology.posweb.data.repository.VentaRepository;
import com.tghtechnology.posweb.service.DetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public DetalleVenta registrarDetalleVenta(DetalleVenta detalleVenta) {
        // Validamos que el Producto y la Venta existen
        validarExistenciaEntidad(detalleVenta.getProducto(), "Producto");
        validarExistenciaEntidad(detalleVenta.getVenta(), "Venta");

        // Aseguramos que la relación no se pierda usando el setter
        detalleVenta.setProducto(detalleVenta.getProducto()); // Usamos el setter explícitamente
        detalleVenta.setVenta(detalleVenta.getVenta());       // Usamos el setter explícitamente

        // Calculamos el subtotal del detalle
        //detalleVenta.setSubtotal(detalleVenta.getCantidad() * detalleVenta.getPrecioUnitario());

        // Guardamos el detalle de la venta
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public List<DetalleVenta> consultarDetallesVenta(Long idVenta) {
        return detalleVentaRepository.finByIdVenta(idVenta);
    }

    @Override
    public DetalleVenta actualizarDetalleVenta(Long idDetalle, Integer nuevaCantidad, Double nuevoPrecioUnitario) {
        Optional<DetalleVenta> detalleVentaOptional = detalleVentaRepository.findById(idDetalle);

        if (detalleVentaOptional.isPresent()) {
            DetalleVenta detalleVenta = detalleVentaOptional.get();

            // Actualizamos solo la cantidad y precio, pero mantenemos las relaciones
            detalleVenta.setCantidad(nuevaCantidad);
            detalleVenta.setSubtotal(nuevaCantidad * nuevoPrecioUnitario);

            // Validamos que el Producto y la Venta siguen siendo válidos
            validarExistenciaEntidad(detalleVenta.getProducto(), "Producto");
            validarExistenciaEntidad(detalleVenta.getVenta(), "Venta");

            // Aseguramos que la relación no se pierda usando el setter
            detalleVenta.setProducto(detalleVenta.getProducto());
            detalleVenta.setVenta(detalleVenta.getVenta());

            // Guardamos el detalle actualizado
            return detalleVentaRepository.save(detalleVenta);
        } else {
            throw new IllegalArgumentException("Detalle de venta no encontrado");
        }
    }

    @Override
    public boolean eliminarDetalleVenta(Long idDetalle) {
        Optional<DetalleVenta> detalleVentaOptional = detalleVentaRepository.findById(idDetalle);

        if (detalleVentaOptional.isPresent()) {
            detalleVentaRepository.deleteById(idDetalle);
            return true;
        } else {
            return false;
        }
    }

    private void validarExistenciaEntidad(Object entidad, String nombreEntidad) {
        if (entidad == null) {
            throw new IllegalArgumentException(nombreEntidad + " no especificado o inválido");
        }

        Optional<?> entidadOptional = Optional.empty();

        // Validamos la existencia del Producto o Venta
        if (nombreEntidad.equals("Producto")) {
            entidadOptional = productoRepository.findById(((Producto) entidad).getIdProducto());
        } else if (nombreEntidad.equals("Venta")) {
            entidadOptional = ventaRepository.findById(((Venta) entidad).getIdVenta());
        } else {
            throw new IllegalArgumentException("Entidad no válida para la validación");
        }

        if (!entidadOptional.isPresent()) {
            throw new IllegalArgumentException(nombreEntidad + " no encontrado");
        }
    }
}
