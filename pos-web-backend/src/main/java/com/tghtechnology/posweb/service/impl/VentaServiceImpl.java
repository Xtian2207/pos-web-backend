package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.entities.DetalleVenta;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.entities.Venta;
import com.tghtechnology.posweb.data.repository.DetalleVentaRepository;
import com.tghtechnology.posweb.data.repository.ProductoRepository;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import com.tghtechnology.posweb.data.repository.VentaRepository;
import com.tghtechnology.posweb.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Registrar venta
    @Override
    public Venta registrarVenta(Venta venta) {
        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un detalle.");
        }

        // Validar existencia del usuario por ID
        if (venta.getUsuario() == null || venta.getUsuario().getIdUsuario() == null) {
            throw new IllegalArgumentException("El usuario que realiza la venta no es válido.");
        }

        Optional<Usuario> usuarioExistente = usuarioRepository.findById(venta.getUsuario().getIdUsuario());
        if (!usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + venta.getUsuario().getIdUsuario());
        }

        // Asignar el usuario a la venta
        venta.setUsuario(usuarioExistente.get());

        // Procesar los detalles de la venta
        double totalVenta = 0;
        for (DetalleVenta detalle : venta.getDetalles()) {
            // Validar el producto en cada detalle
            if (detalle.getProducto() == null || detalle.getProducto().getIdProducto() == null) {
                throw new IllegalArgumentException("Producto inválido en detalle de venta.");
            }

            Optional<Producto> productoExistente = productoRepository.findById(detalle.getProducto().getIdProducto());
            if (!productoExistente.isPresent()) {
                throw new IllegalArgumentException(
                        "Producto no encontrado con ID: " + detalle.getProducto().getIdProducto());
            }

            Producto producto = productoExistente.get();

            // Verificar que haya suficiente cantidad de producto
            if (producto.getCantidad() < detalle.getCantidad()) {
                throw new IllegalArgumentException("No hay suficiente cantidad de " + producto.getNombreProducto() +
                        " en inventario. Solo hay " + producto.getCantidad() + " disponible(s).");
            }

            // Asignar el producto al detalle
            detalle.setProducto(producto);

            // Validar que el precio del producto no sea nulo
            if (producto.getPrecio() == null) {
                throw new IllegalArgumentException(
                        "El producto con ID " + producto.getIdProducto() + " no tiene un precio definido.");
            }

            // Calcular el subtotal para el detalle
            double subtotal = detalle.getCantidad() * producto.getPrecio();
            detalle.setSubtotal(subtotal);

            // Actualizar la cantidad del producto en inventario
            producto.setCantidad(producto.getCantidad() - detalle.getCantidad());
            productoRepository.save(producto); // Actualiza el inventario

            // Asignar la venta al detalle
            detalle.setVenta(venta);

            // Acumular el total de la venta
            totalVenta += subtotal;
        }

        // Asignar el total a la venta
        venta.setTotal(totalVenta);

        // Asignar fecha y hora de la venta
        venta.setFechaVenta(LocalDate.now());
        venta.setHoraVenta(LocalTime.now());

        // Guardar la venta (esto también guarda los detalles gracias a la relación en
        // cascada)
        Venta ventaGuardada = ventaRepository.save(venta);

        // Guardar los detalles relacionados con la venta
        detalleVentaRepository.saveAll(venta.getDetalles());

        return ventaGuardada;
    }

    // Consultar todas las ventas
    @Override
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    // Obtener venta por ID
    @Override
    public Optional<Venta> obtenerVentaPorId(Long idVenta) {
        return ventaRepository.findById(idVenta);
    }

    // Actualizar venta
    @Override
    public Venta actualizarVenta(Long idVenta, Venta ventaActualizada) {
        Optional<Venta> ventaExistente = ventaRepository.findById(idVenta);
        if (ventaExistente.isPresent()) {
            Venta venta = ventaExistente.get();

            // Si el usuario ha sido asignado, lo actualizamos
            if (ventaActualizada.getUsuario() != null && ventaActualizada.getUsuario().getIdUsuario() != null) {
                Optional<Usuario> usuarioExistente = usuarioRepository
                        .findById(ventaActualizada.getUsuario().getIdUsuario());
                if (usuarioExistente.isPresent()) {
                    venta.setUsuario(usuarioExistente.get());
                } else {
                    throw new IllegalArgumentException("Usuario no encontrado");
                }
            }

            // Actualizamos los detalles de la venta por IDs
            if (ventaActualizada.getDetalles() != null && !ventaActualizada.getDetalles().isEmpty()) {
                for (DetalleVenta detalleVenta : ventaActualizada.getDetalles()) {
                    Optional<Producto> productoExistente = productoRepository
                            .findById(detalleVenta.getProducto().getIdProducto());
                    if (productoExistente.isPresent()) {
                        detalleVenta.setProducto(productoExistente.get());
                        detalleVenta.setVenta(venta);
                    } else {
                        throw new IllegalArgumentException(
                                "Producto no encontrado con ID: " + detalleVenta.getProducto().getIdProducto());
                    }
                }
                detalleVentaRepository.saveAll(ventaActualizada.getDetalles());
            }

            // Actualizamos otros detalles de la venta
            venta.setMetodoPago(ventaActualizada.getMetodoPago());
            venta.setTotal(ventaActualizada.getTotal());
            venta.setFechaVenta(ventaActualizada.getFechaVenta());
            venta.setHoraVenta(ventaActualizada.getHoraVenta());

            return ventaRepository.save(venta);
        } else {
            throw new IllegalArgumentException("Venta no encontrada con ID: " + idVenta);
        }
    }

    // Eliminar venta
    @Override
    public void eliminarVenta(Long idVenta) {
        if (ventaRepository.existsById(idVenta)) {
            ventaRepository.deleteById(idVenta);
        } else {
            throw new IllegalArgumentException("Venta no encontrada con ID: " + idVenta);
        }
    }

    // Obtener ventas por usuario
    @Override
    public List<Venta> obtenerVentasPorUsuario(Long usuarioId) {
        return ventaRepository.findByUsuario_IdUsuario(usuarioId);
    }

    // Obtener ventas por rango de fechas
    @Override
    public List<Venta> obtenerVentasPorFecha(Date fechaInicio, Date fechaFin) {
        return ventaRepository.findVentasByFechaVentaBetween(fechaInicio, fechaFin);
    }
}
