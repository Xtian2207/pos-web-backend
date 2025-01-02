package com.tghtechnology.posweb.services;

import com.tghtechnology.posweb.data.entities.*;
import com.tghtechnology.posweb.data.repository.DetalleVentaRepository;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import com.tghtechnology.posweb.data.repository.VentaRepository;
import com.tghtechnology.posweb.service.impl.VentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class VentaServiceTest {

    /* 
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private DetalleVentaRepository detalleVentaRepository;

    @InjectMocks
    private VentaServiceImpl ventaService;

    private Usuario usuarioMock;
    private Producto productoMock;
    private DetalleVenta detalleMock;
    private Venta ventaMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Usuario Mock
        usuarioMock = new Usuario(1L, "John", "Doe", "johndoe@example.com", "password", null, EstadoUsuario.ACTIVO);

        // Producto Mock
        productoMock = new Producto(1L, "Producto Test", "Descripción Test", 100.0, 10,
                EstadoProducto.DISPONIBLE, null);

        // DetalleVenta Mock
        detalleMock = new DetalleVenta(1L, null, productoMock, 2, 100.0);

        // Venta Mock
        ventaMock = new Venta(1L, usuarioMock, List.of(detalleMock), 200.0, LocalTime.now(), LocalDate.now(),
                MetodoPago.EFECTIVO);
        detalleMock.setVenta(ventaMock);

        // Configuración de los mocks
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(ventaRepository.save(any(Venta.class))).thenReturn(ventaMock);
        when(detalleVentaRepository.saveAll(anyList())).thenReturn(List.of(detalleMock));
    }

    @Test
    void registrarVenta_exitoso() {
        // Ejecutamos el metodo de registrarVenta
        Venta ventaGuardada = ventaService.registrarVenta(ventaMock);

        // Verificamos que la venta ha sido guardada correctamente
        assertNotNull(ventaGuardada);
        assertEquals(ventaMock.getTotal(), ventaGuardada.getTotal());
        assertEquals(ventaMock.getUsuario().getIdUsuario(), ventaGuardada.getUsuario().getIdUsuario());
        assertEquals(ventaMock.getDetalles().size(), ventaGuardada.getDetalles().size());
        verify(ventaRepository, times(1)).save(ventaMock);
        verify(detalleVentaRepository, times(1)).saveAll(ventaMock.getDetalles());
    }

    @Test
    void registrarVenta_fallaPorUsuarioInvalido() {
        ventaMock.setUsuario(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ventaService.registrarVenta(ventaMock);
        });

        assertEquals("El usuario que realiza la venta no es válido", exception.getMessage());
        verify(usuarioRepository, never()).findById(anyLong());
    }

    @Test
    void registrarVenta_fallaPorProductoInvalidoEnDetalle() {
        DetalleVenta detalleMock = new DetalleVenta();
        detalleMock.setProducto(null); // Producto nulo

        // Crea una venta con el detalle inválido
        Venta ventaMock = new Venta();
        ventaMock.setDetalles(List.of(detalleMock));

        // Asumiendo que el usuario existe
        Usuario usuarioMock = new Usuario();
        usuarioMock.setIdUsuario(1L); 

        ventaMock.setUsuario(usuarioMock);

        // Aquí estamos verificando que la excepción esperada sea lanzada
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ventaService.registrarVenta(ventaMock);
        });

        // Verificamos el mensaje de la excepción
        assertEquals("Producto inválido en detalle de venta", exception.getMessage());

        // Aseguramos que no se haya guardado la venta
        verify(ventaRepository, never()).save(any(Venta.class));
    }

    @Test
    void listarVentas_retornaVentas() {
        when(ventaRepository.findAll()).thenReturn(List.of(ventaMock));

        List<Venta> result = ventaService.listarVentas();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getIdVenta());
        verify(ventaRepository, times(1)).findAll();
    }

    @Test
    void obtenerVentaPorId_exitoso() {
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(ventaMock));

        Optional<Venta> result = ventaService.obtenerVentaPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdVenta());
        verify(ventaRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerVentaPorId_falla() {
        when(ventaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Venta> result = ventaService.obtenerVentaPorId(1L);

        assertFalse(result.isPresent());
        verify(ventaRepository, times(1)).findById(1L);
    }

    @Test
    void eliminarVenta_exitoso() {
        when(ventaRepository.existsById(1L)).thenReturn(true);

        ventaService.eliminarVenta(1L);

        verify(ventaRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarVenta_fallaPorIdInexistente() {
        when(ventaRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ventaService.eliminarVenta(1L);
        });

        assertEquals("Venta no encontrada con ID: 1", exception.getMessage());
        verify(ventaRepository, never()).deleteById(1L);
    }
        */
}