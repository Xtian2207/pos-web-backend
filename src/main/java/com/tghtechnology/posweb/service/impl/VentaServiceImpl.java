package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.dto.ClienteDTO;
import com.tghtechnology.posweb.data.dto.DetalleVentaDTO;
import com.tghtechnology.posweb.data.dto.VentaDTO;
import com.tghtechnology.posweb.data.dto.VentaNotificationDTO;
import com.tghtechnology.posweb.data.entities.Cliente;
import com.tghtechnology.posweb.data.entities.DetalleVenta;
import com.tghtechnology.posweb.data.entities.MetodoPago;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.entities.TipoVenta;
import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.entities.Venta;
import com.tghtechnology.posweb.data.mapper.ClienteMapper;
import com.tghtechnology.posweb.data.mapper.VentaMapper;
import com.tghtechnology.posweb.data.repository.ClienteRepository;
import com.tghtechnology.posweb.data.repository.DetalleVentaRepository;
import com.tghtechnology.posweb.data.repository.ProductoRepository;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import com.tghtechnology.posweb.data.repository.VentaRepository;
import com.tghtechnology.posweb.exceptions.ResourceNotFoundException;
import com.tghtechnology.posweb.service.VentaNotificationService;
import com.tghtechnology.posweb.service.VentaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private VentaMapper ventaMapper;

    @Autowired
    private VentaNotificationService ventaNotificationService;

    @Override
    public VentaDTO registrarVenta(VentaDTO ventaDTO) {
        // Validaciones iniciales
        if (ventaDTO.getDetalles() == null || ventaDTO.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un detalle.");
        }

        // Validar existencia del usuario por ID
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(ventaDTO.getIdUsuario());
        if (!usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + ventaDTO.getIdUsuario());
        }

        // Validar y procesar el cliente
        ClienteDTO clienteDTO = ventaDTO.getCliente();
        Cliente clienteEntity = null;

        if (clienteDTO != null) {
            // Si el cliente no tiene ID, se crea uno nuevo
            if (clienteDTO.getIdCliente() == null) {
                // Validar que los datos del cliente sean correctos
                if (!clienteDTO.validarNumeroDocumento()) {
                    throw new IllegalArgumentException("El número de documento del cliente no es válido.");
                }

                // Verificar si el cliente ya existe por su documento
                Optional<Cliente> clienteExistente = clienteRepository.findByDocument(clienteDTO.getDocument());
                if (clienteExistente.isPresent()) {
                    clienteEntity = clienteExistente.get();
                } else {
                    // Crear un nuevo cliente
                    clienteEntity = clienteMapper.toEntity(clienteDTO);
                    clienteRepository.save(clienteEntity);
                }
            } else {
                // Si el cliente tiene ID, validar que exista en la base de datos
                Optional<Cliente> clienteExistente = clienteRepository.findById(clienteDTO.getIdCliente());
                if (clienteExistente.isPresent()) {
                    clienteEntity = clienteExistente.get();
                } else {
                    throw new IllegalArgumentException("Cliente no encontrado con ID: " + clienteDTO.getIdCliente());
                }
            }
        }

        // Crear la venta
        Usuario usuario = usuarioExistente.get();
        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setDetalles(new ArrayList<>()); // Inicializar la lista de detalles
        venta.setCliente(clienteEntity); // Asignar el cliente a la venta

        if (ventaDTO.getTipoDeVenta() != null) {
            try {
                venta.setTipoDeVenta(TipoVenta.valueOf(ventaDTO.getTipoDeVenta().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Tipo de venta inválido.");
            }
        } else {
            throw new IllegalArgumentException("Tipo de venta no puede ser nulo.");
        }

        // Procesar los detalles de la venta
        double totalVenta = 0;
        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
            Optional<Producto> productoExistente = productoRepository.findById(detalleDTO.getIdProducto());
            if (!productoExistente.isPresent()) {
                throw new IllegalArgumentException("Producto no encontrado con ID: " + detalleDTO.getIdProducto());
            }

            Producto producto = productoExistente.get();

            if (producto.getCantidad() < detalleDTO.getCantidad()) {
                throw new IllegalArgumentException("No hay suficiente cantidad de " + producto.getNombreProducto());
            }

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setSubtotal(detalleDTO.getCantidad() * producto.getPrecio());
            detalle.setVenta(venta);

            // Actualizar inventario
            producto.setCantidad(producto.getCantidad() - detalleDTO.getCantidad());
            productoRepository.save(producto);

            totalVenta += detalle.getSubtotal();
            venta.getDetalles().add(detalle);
        }

        // Asignar datos adicionales a la venta
        venta.setTotal(totalVenta);

        // Asignar fecha sin hora, minutos, segundos ni milisegundos
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0); // Establecer la hora a 00
        calendar.set(Calendar.MINUTE, 0); // Establecer los minutos a 00
        calendar.set(Calendar.SECOND, 0); // Establecer los segundos a 00
        calendar.set(Calendar.MILLISECOND, 0); // Establecer los milisegundos a 00
        venta.setFechaVenta(calendar.getTime());

        // Asignar hora sin nanosegundos
        LocalTime horaActual = LocalTime.now().withNano(0); // Eliminar nanosegundos
        venta.setHoraVenta(horaActual);

        // Validar y asignar el método de pago
        if (ventaDTO.getMetodoPago() != null) {
            try {
                venta.setMetodoPago(MetodoPago.valueOf(ventaDTO.getMetodoPago()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Método de pago inválido.");
            }
        } else {
            throw new IllegalArgumentException("Método de pago no puede ser nulo.");
        }

        // Guardar la venta y los detalles
        Venta ventaGuardada = ventaRepository.save(venta);

        //for (DetalleVenta detalle : venta.getDetalles()) {
           // detalleVentaRepository.save(detalle);
        //}

        try {
            // Formatear los datos correctamente
            String totalFormateado = String.format("S/ %.2f", ventaGuardada.getTotal());
            String fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(ventaGuardada.getFechaVenta());

            // Crear objeto DTO con la información estructurada
            VentaNotificationDTO notificacion = new VentaNotificationDTO(
                    "Nueva venta registrada",
                    ventaGuardada.getIdVenta(),
                    totalFormateado,
                    ventaGuardada.getUsuario().getNombre(),
                    fechaFormateada);

            ventaNotificationService.enviarNotificacionVenta(notificacion);

            return ventaMapper.toDTO(ventaGuardada);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw exception;
        }

    }

    @Override
    public List<VentaDTO> listarVentas() {
        return ventaRepository.findAll()
                .stream()
                .map(ventaMapper::toDTO)
                .toList();
    }

    @Override
    public Optional<VentaDTO> obtenerVentaPorId(Long idVenta) {
        return ventaRepository.findById(idVenta)
                .map(ventaMapper::toDTO);
    }

    @Override
    public VentaDTO actualizarVenta(Long idVenta, VentaDTO ventaDTO) {
        Optional<Venta> ventaExistente = ventaRepository.findById(idVenta);
        if (!ventaExistente.isPresent()) {
            throw new IllegalArgumentException("Venta no encontrada con ID: " + idVenta);
        }

        Venta venta = ventaExistente.get();

        // Actualizar usuario (si se proporciona un nuevo usuario)
        if (ventaDTO.getIdUsuario() != null) {
            Optional<Usuario> usuarioExistente = usuarioRepository.findById(ventaDTO.getIdUsuario());
            if (!usuarioExistente.isPresent()) {
                throw new IllegalArgumentException("Usuario no encontrado con ID: " + ventaDTO.getIdUsuario());
            }
            venta.setUsuario(usuarioExistente.get());
        }

        // Actualizar método de pago (si se proporciona un nuevo método de pago)
        if (ventaDTO.getMetodoPago() != null) {
            try {
                venta.setMetodoPago(MetodoPago.valueOf(ventaDTO.getMetodoPago().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Método de pago inválido.");
            }
        }

        // Actualizar tipo de venta
        if (ventaDTO.getTipoDeVenta() != null) {
            try {
                venta.setTipoDeVenta(TipoVenta.valueOf(ventaDTO.getTipoDeVenta().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Tipo de venta inválido.");
            }
        }

        // Si se proporcionan nuevos detalles de venta, actualizamos el total
        double totalVenta = venta.getTotal(); // Iniciamos el total con el valor actual
        if (ventaDTO.getDetalles() != null && !ventaDTO.getDetalles().isEmpty()) {
            // Actualizamos los detalles de venta
            for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
                Optional<Producto> productoExistente = productoRepository.findById(detalleDTO.getIdProducto());
                if (!productoExistente.isPresent()) {
                    throw new IllegalArgumentException("Producto no encontrado con ID: " + detalleDTO.getIdProducto());
                }

                Producto producto = productoExistente.get();

                // Validar que haya suficiente stock
                if (producto.getCantidad() < detalleDTO.getCantidad()) {
                    throw new IllegalArgumentException("No hay suficiente cantidad de " + producto.getNombreProducto());
                }

                // Buscar si el detalle ya existe en la venta
                Optional<DetalleVenta> detalleExistente = venta.getDetalles().stream()
                        .filter(d -> d.getProducto().getIdProducto().equals(detalleDTO.getIdProducto()))
                        .findFirst();

                if (detalleExistente.isPresent()) {
                    // Si el detalle existe, actualizamos la cantidad y el subtotal
                    DetalleVenta detalle = detalleExistente.get();
                    int cantidadAnterior = detalle.getCantidad();
                    detalle.setCantidad(detalleDTO.getCantidad());
                    detalle.setSubtotal(detalleDTO.getCantidad() * producto.getPrecio());

                    // Ajustar el total de la venta
                    totalVenta -= cantidadAnterior * producto.getPrecio(); // Restamos el subtotal anterior
                    totalVenta += detalle.getSubtotal(); // Sumamos el nuevo subtotal

                    // Guardar el detalle actualizado en la base de datos
                    detalleVentaRepository.save(detalle);

                } else {
                    // Si el detalle no existe, creamos uno nuevo
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setProducto(producto);
                    detalle.setCantidad(detalleDTO.getCantidad());
                    detalle.setSubtotal(detalleDTO.getCantidad() * producto.getPrecio());
                    detalle.setVenta(venta);

                    // Actualizar inventario
                    producto.setCantidad(producto.getCantidad() - detalleDTO.getCantidad());
                    productoRepository.save(producto);

                    // Agregar al total
                    totalVenta += detalle.getSubtotal();
                    venta.getDetalles().add(detalle); // Añadir el nuevo detalle

                    // Guardar el nuevo detalle en la base de datos
                    detalleVentaRepository.save(detalle);
                }
            }
        }

        // Actualizar el total de la venta
        venta.setTotal(totalVenta);

        // Guardar la venta actualizada
        Venta ventaGuardada = ventaRepository.save(venta);

        return ventaMapper.toDTO(ventaGuardada);
    }

    @Override
    public void eliminarVenta(Long idVenta) {
        if (!ventaRepository.existsById(idVenta)) {
            throw new IllegalArgumentException("Venta no encontrada con ID: " + idVenta);
        }
        ventaRepository.deleteById(idVenta);
    }

    @Override
    public List<VentaDTO> obtenerVentasPorUsuario(Long usuarioId) {
        List<Venta> ventas = ventaRepository.findByUsuario_IdUsuario(usuarioId);

        List<VentaDTO> ventasDTO = ventas.stream()
                .map(venta -> ventaMapper.toDTO(venta))
                .collect(Collectors.toList());

        return ventasDTO;
    }

    @Override
    public List<VentaDTO> obtenerVentasPorFecha(Date fechaInicio, Date fechaFin) {
        List<Venta> ventas = ventaRepository.findVentasByFechaVentaBetween(fechaInicio, fechaFin);

        List<VentaDTO> ventasDTO = ventas.stream()
                .map(venta -> ventaMapper.toDTO(venta))
                .collect(Collectors.toList());
        return ventasDTO;
    }

    @Override
    public List<VentaDTO> obtenerVentasPorAnio(int anio) {
        // Obtenemos el primer día del año
        Calendar calendarInicio = Calendar.getInstance();
        calendarInicio.set(anio, Calendar.JANUARY, 1, 0, 0, 0);
        Date fechaInicio = calendarInicio.getTime();

        // Obtenemos el última día del año
        Calendar calendarFin = Calendar.getInstance();
        calendarFin.set(anio, Calendar.DECEMBER, 31, 23, 59, 59);
        Date fechaFin = calendarFin.getTime();

        List<Venta> ventas = ventaRepository.findVentasByFechaVentaBetween(fechaInicio, fechaFin);

        return ventas.stream()
                .map(venta -> ventaMapper.toDTO(venta))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VentaDTO agregarProductoPorCodigoBarras(VentaDTO ventaDTO, String codigoBarras)
            throws ResourceNotFoundException {
        Producto producto = productoRepository.findByCodigoBarras(codigoBarras)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        Venta venta;
        if (ventaDTO.getIdVenta() == null) {
            if (ventaDTO.getIdUsuario() == null) {
                throw new IllegalArgumentException("El ID de usuario es requerido");
            }
            if (ventaDTO.getMetodoPago() == null) {
                throw new IllegalArgumentException("Método de pago es requerido");
            }
            if (ventaDTO.getTipoDeVenta() == null) {
                throw new IllegalArgumentException("Tipo de venta es requerido");
            }

            venta = new Venta();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            venta.setFechaVenta(calendar.getTime());

            venta.setHoraVenta(LocalTime.now().withNano(0));

            venta.setDetalles(new ArrayList<>());

            Usuario usuario = usuarioRepository.findById(ventaDTO.getIdUsuario())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            venta.setUsuario(usuario);

            try {
                venta.setMetodoPago(MetodoPago.valueOf(ventaDTO.getMetodoPago().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Método de pago inválido: " + ventaDTO.getMetodoPago());
            }

            try {
                venta.setTipoDeVenta(TipoVenta.valueOf(ventaDTO.getTipoDeVenta().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Tipo de venta inválido: " + ventaDTO.getTipoDeVenta());
            }

            if (ventaDTO.getCliente() != null) {
                Cliente cliente = clienteRepository.findById(ventaDTO.getCliente().getIdCliente())
                        .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
                venta.setCliente(cliente);
            }
        } else {
            venta = ventaRepository.findById(ventaDTO.getIdVenta())
                    .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada"));
        }

        agregarProductoAVenta(venta, producto, 1);

        producto.setCantidad(producto.getCantidad() - 1);
        productoRepository.save(producto);

        venta.setTotal(calcularTotalVenta(venta));

        Venta ventaGuardada = ventaRepository.save(venta);
        VentaDTO resultado = ventaMapper.toDTO(ventaGuardada);

        if (ventaGuardada.getCliente() != null) {
            resultado.setNombreCliente(ventaGuardada.getCliente().getNombre());
        }

        return resultado;
    }

    private void agregarProductoAVenta(Venta venta, Producto producto, int cantidad) {
        venta.getDetalles().stream()
                .filter(d -> d.getProducto().getIdProducto().equals(producto.getIdProducto()))
                .findFirst()
                .ifPresentOrElse(
                        detalle -> {
                            detalle.setCantidad(detalle.getCantidad() + cantidad);
                            detalle.setSubtotal(detalle.getCantidad() * producto.getPrecio());
                        },
                        () -> {
                            DetalleVenta nuevoDetalle = new DetalleVenta();
                            nuevoDetalle.setProducto(producto);
                            nuevoDetalle.setCantidad(cantidad);
                            nuevoDetalle.setSubtotal(cantidad * producto.getPrecio());
                            nuevoDetalle.setVenta(venta);
                            venta.getDetalles().add(nuevoDetalle);
                        });
    }

    private double calcularTotalVenta(Venta venta) {
        return venta.getDetalles().stream()
                .mapToDouble(DetalleVenta::getSubtotal)
                .sum();
    }

}
