package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.DetalleVenta;
import com.tghtechnology.posweb.data.entities.Venta;
import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.repository.DetalleVentaRepository;
import com.tghtechnology.posweb.data.repository.VentaRepository;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registrar venta
    public Venta registrarVenta(Venta venta) {
        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un producto");
        }

        // Validar existencia del usuario
        if (venta.getUsuario() == null || venta.getUsuario().getIdUsuario() == null) {
            throw new IllegalArgumentException("El usuario que realiza la venta no es válido");
        }

        // Si el usuario no está asociado correctamente, buscamos al usuario y lo asignamos
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(venta.getUsuario().getIdUsuario());
        if (!usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        // Asignamos el usuario a la venta
        venta.setUsuario(usuarioExistente.get());

        // Validar que los detalles tengan productos válidos
        venta.getDetalles().forEach(detalle -> {
            if (detalle.getProducto() == null || detalle.getProducto().getIdProducto() == null) {
                throw new IllegalArgumentException("Producto inválido en detalle de venta");
            }
        });

        // Calcular el total de la venta
        Double totalVenta = venta.getDetalles().stream()
                .mapToDouble(detalle -> detalle.getCantidad() * detalle.getProducto().getPrecio())
                .sum();

        venta.setTotal(totalVenta);
        venta.setFechaVenta(LocalDate.now());
        venta.setHoraVenta(LocalTime.now());

        // Asociamos los detalles a la venta
        venta.getDetalles().forEach(detalle -> detalle.setVenta(venta));

        // Guardamos la venta y sus detalles
        Venta ventaGuardada = ventaRepository.save(venta);
        detalleVentaRepository.saveAll(venta.getDetalles());

        return ventaGuardada;
    }

    // Consultar todas las ventas
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    // Obtener venta por ID
    public Optional<Venta> obtenerVentaPorId(Long idVenta) {
        return ventaRepository.findById(idVenta);
    }

    // Actualizar venta
    public Venta actualizarVenta(Long idVenta, Venta ventaActualizada) {
        Optional<Venta> ventaExistente = ventaRepository.findById(idVenta);
        if (ventaExistente.isPresent()) {
            Venta venta = ventaExistente.get();

            // Si el usuario no ha sido asignado, lo actualizamos
            if (ventaActualizada.getUsuario() != null && ventaActualizada.getUsuario().getIdUsuario() != null) {
                Optional<Usuario> usuarioExistente = usuarioRepository.findById(ventaActualizada.getUsuario().getIdUsuario());
                if (usuarioExistente.isPresent()) {
                    venta.setUsuario(usuarioExistente.get());
                } else {
                    throw new IllegalArgumentException("Usuario no encontrado");
                }
            }

            // Actualizamos los detalles de la venta
            if (ventaActualizada.getDetalles() != null && !ventaActualizada.getDetalles().isEmpty()) {
                for (DetalleVenta detalleVenta : ventaActualizada.getDetalles()) {
                    detalleVenta.setVenta(venta);
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
    public void eliminarVenta(Long idVenta) {
        if (ventaRepository.existsById(idVenta)) {
            ventaRepository.deleteById(idVenta);
        } else {
            throw new IllegalArgumentException("Venta no encontrada con ID: " + idVenta);
        }
    }

    // Obtener ventas por usuario
    public List<Venta> obtenerVentasPorUsuario(Long usuarioId) {
        return ventaRepository.findByUsuarioId(usuarioId);
    }

    // Obtener ventas por rango de fechas
    public List<Venta> obtenerVentasPorFecha(Date fechaInicio, Date fechaFin) {
        return ventaRepository.findVentasByFechaVentaBetween(fechaInicio, fechaFin);
    }
}
