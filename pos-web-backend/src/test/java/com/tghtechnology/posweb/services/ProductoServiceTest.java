package com.tghtechnology.posweb.services;

import com.tghtechnology.posweb.data.entities.Categoria;
import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.repository.CategoriaRepository;
import com.tghtechnology.posweb.data.repository.ProductoRepository;
import com.tghtechnology.posweb.service.impl.ProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto producto;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoria = new Categoria(1L, "Categoria 1");
        producto = new Producto(1L, "Producto 1", "Descripcion", 100.0, 10, EstadoProducto.DISPONIBLE, categoria);
    }

    @Test
    void registrarProducto_success() throws Exception {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto result = productoService.registrarProducto(1L, producto);

        assertNotNull(result);
        assertEquals("Producto 1", result.getNombreProducto());
        verify(categoriaRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void registrarProducto_categoriaNoEncontrada() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            productoService.registrarProducto(1L, producto);
        });

        assertEquals("Categoría con ID 1 no encontrada", exception.getMessage());
        verify(categoriaRepository, times(1)).findById(1L);
        verify(productoRepository, times(0)).save(producto);
    }

    @Test
    void listarProductos_success() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto));

        var productos = productoService.listarProductos();

        assertNotNull(productos);
        assertFalse(productos.isEmpty());
        assertEquals(1, productos.size());
    }

    @Test
    void buscarPorNombre_found() {
        when(productoRepository.findByNombreProducto("Producto 1")).thenReturn(Optional.of(producto));

        Optional<Producto> result = productoService.buscarPorNombre("Producto 1");

        assertTrue(result.isPresent());
        assertEquals("Producto 1", result.get().getNombreProducto());
    }

    @Test
    void buscarPorNombre_notFound() {
        when(productoRepository.findByNombreProducto("Producto 2")).thenReturn(Optional.empty());

        Optional<Producto> result = productoService.buscarPorNombre("Producto 2");

        assertFalse(result.isPresent());
    }

    @Test
    void buscarPorId_success() {
        when(productoRepository.findByIdProducto(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> result = productoService.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdProducto());
    }

    @Test
    void buscarPorId_notFound() {
        when(productoRepository.findByIdProducto(2L)).thenReturn(Optional.empty());

        Optional<Producto> result = productoService.buscarPorId(2L);

        assertFalse(result.isPresent());
    }

    @Test
    void actualizarProducto_success() {
        Producto productoActualizado = new Producto(1L, "Producto Actualizado", "Descripcion Actualizada", 150.0, 15, EstadoProducto.DISPONIBLE, categoria);
        when(productoRepository.findByIdProducto(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(producto)).thenReturn(productoActualizado);

        Producto result = productoService.actualizarProducto(1L, productoActualizado);

        assertNotNull(result);
        assertEquals("Producto Actualizado", result.getNombreProducto());
        assertEquals(150.0, result.getPrecio());
    }

    @Test
    void actualizarProducto_notFound() {
        Producto productoActualizado = new Producto(2L, "Producto Inexistente", "Descripcion", 100.0, 10, EstadoProducto.DISPONIBLE, categoria);
        when(productoRepository.findByIdProducto(2L)).thenReturn(Optional.empty());

        Producto result = productoService.actualizarProducto(2L, productoActualizado);

        assertNull(result);
    }

    @Test
    void eliminarProducto_success() {
        doNothing().when(productoRepository).deleteById(1L);

        productoService.eliminarProducto(1L);

        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    void cambiarEstadoProducto_success() {
        when(productoRepository.findByIdProducto(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto result = productoService.cambiarEstadoProducto(1L, EstadoProducto.NO_DISPONIBLE);

        assertNotNull(result);
        assertEquals(EstadoProducto.NO_DISPONIBLE, result.getEstado());
    }

    @Test
    void cambiarEstadoProducto_notFound() {
        when(productoRepository.findByIdProducto(2L)).thenReturn(Optional.empty());

        Producto result = productoService.cambiarEstadoProducto(2L, EstadoProducto.NO_DISPONIBLE);

        assertNull(result);
    }

    @Test
    void obtenerProductosPorEstado_success() {
        when(productoRepository.findByEstado(EstadoProducto.DISPONIBLE)).thenReturn(Arrays.asList(producto));

        var productos = productoService.obtenerProductosPorEstado(EstadoProducto.DISPONIBLE);

        assertNotNull(productos);
        assertFalse(productos.isEmpty());
        assertEquals(1, productos.size());
    }

    @Test
    void obtenerProductosPorCategoria_success() {
        when(productoRepository.findByCategoriaIdCategoria(1L)).thenReturn(Arrays.asList(producto));

        var productos = productoService.obtenerProductosPorCategoria(1L);

        assertNotNull(productos);
        assertFalse(productos.isEmpty());
        assertEquals(1, productos.size());
    }
}
