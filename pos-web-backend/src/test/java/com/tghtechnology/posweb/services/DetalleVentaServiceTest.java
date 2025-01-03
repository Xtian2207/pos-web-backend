package com.tghtechnology.posweb.services;

import com.tghtechnology.posweb.data.entities.DetalleVenta;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.entities.Venta;
import com.tghtechnology.posweb.data.repository.DetalleVentaRepository;
import com.tghtechnology.posweb.data.repository.ProductoRepository;
import com.tghtechnology.posweb.data.repository.VentaRepository;
import com.tghtechnology.posweb.service.impl.DetalleVentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DetalleVentaServiceTest {

    @Mock
    private DetalleVentaRepository detalleVentaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private DetalleVentaServiceImpl detalleVentaService;

    private Producto producto;
    private Venta venta;
    private DetalleVenta detalleVenta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombreProducto("Producto A");

        venta = new Venta();
        venta.setIdVenta(1L);

        detalleVenta = new DetalleVenta();
        detalleVenta.setIdDetalle(1L);
        detalleVenta.setProducto(producto);
        detalleVenta.setVenta(venta);
        detalleVenta.setCantidad(2);
    }

    @Test
    void registrarDetalleVenta() {
        // Mocking the repository calls
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));
        when(detalleVentaRepository.save(detalleVenta)).thenReturn(detalleVenta);

        DetalleVenta result = detalleVentaService.registrarDetalleVenta(detalleVenta);

        assertNotNull(result);
        assertEquals(20.0, result.getSubtotal());
        verify(detalleVentaRepository, times(1)).save(detalleVenta);
    }

    @Test
    void registrarDetalleVenta_ProductoNoExistente() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            detalleVentaService.registrarDetalleVenta(detalleVenta);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    void consultarDetallesVenta() {
        when(detalleVentaRepository.finByIdVenta(1L)).thenReturn(List.of(detalleVenta));

        var result = detalleVentaService.consultarDetallesVenta(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void actualizarDetalleVenta() {
        when(detalleVentaRepository.findById(1L)).thenReturn(Optional.of(detalleVenta));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));
        when(detalleVentaRepository.save(detalleVenta)).thenReturn(detalleVenta);

        DetalleVenta updatedDetalle = detalleVentaService.actualizarDetalleVenta(1L, 3, 12.0);

        assertNotNull(updatedDetalle);
        assertEquals(36.0, updatedDetalle.getSubtotal());
        assertEquals(3, updatedDetalle.getCantidad());
    }

    @Test
    void actualizarDetalleVenta_NoEncontrado() {
        when(detalleVentaRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            detalleVentaService.actualizarDetalleVenta(1L, 3, 12.0);
        });

        assertEquals("Detalle de venta no encontrado", exception.getMessage());
    }

    @Test
    void eliminarDetalleVenta() {
        when(detalleVentaRepository.findById(1L)).thenReturn(Optional.of(detalleVenta));

        boolean result = detalleVentaService.eliminarDetalleVenta(1L);

        assertTrue(result);
        verify(detalleVentaRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarDetalleVenta_NoEncontrado() {
        when(detalleVentaRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = detalleVentaService.eliminarDetalleVenta(1L);

        assertFalse(result);
        verify(detalleVentaRepository, times(0)).deleteById(1L);
    }
}
