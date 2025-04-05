package com.tghtechnology.posweb.services;

import com.tghtechnology.posweb.data.dto.ProductoDTO;
import com.tghtechnology.posweb.data.entities.Categoria;
import com.tghtechnology.posweb.data.entities.EstadoProducto;
import com.tghtechnology.posweb.data.entities.Producto;
import com.tghtechnology.posweb.data.mapper.ProductoMapper;
import com.tghtechnology.posweb.data.repository.CategoriaRepository;
import com.tghtechnology.posweb.data.repository.ProductoRepository;
import com.tghtechnology.posweb.service.impl.ProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProductoMapper productoMapper;

    private Producto producto;
    private ProductoDTO productoDTO;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        categoria = new Categoria();
        categoria.setIdCategoria(1L);
        categoria.setNombreCategoria("Electrónicos");

        producto = new Producto();
        producto.setIdProducto(1L);
        producto.setNombreProducto("Laptop");
        producto.setDescripcion("Laptop de última generación");
        producto.setPrecio(1200.0);
        producto.setCantidad(10);
        producto.setEstado(EstadoProducto.DISPONIBLE);
        producto.setCategoria(categoria);

        productoDTO = new ProductoDTO();
        productoDTO.setIdProducto(1L);
        productoDTO.setNombreProducto("Laptop");
        productoDTO.setDescripcion("Laptop de última generación");
        productoDTO.setPrecio(1200.0);
        productoDTO.setCantidad(10);
        productoDTO.setEstado(EstadoProducto.DISPONIBLE);
        productoDTO.setCategoria(categoria);
    }

    /* 
    @Test
    void registrarProducto() {
        when(categoriaRepository.findById(anyLong())).thenReturn(Optional.of(categoria));
        when(productoMapper.toEntity(any(ProductoDTO.class))).thenReturn(producto);
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(productoMapper.toDTO(any(Producto.class))).thenReturn(productoDTO);

        ProductoDTO resultado = productoService.registrarProducto(1L, productoDTO);

        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getNombreProducto());
        verify(categoriaRepository).findById(anyLong());
        verify(productoRepository).save(any(Producto.class));
    }
    */
    @Test
    void listarProductos() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto));
        when(productoMapper.toDTO(any(Producto.class))).thenReturn(productoDTO);

        List<ProductoDTO> resultado = productoService.listarProductos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(productoRepository).findAll();
    }

    @Test
    void buscarPorNombre() {
        when(productoRepository.findByNombreProducto(anyString())).thenReturn(Optional.of(producto));
        when(productoMapper.toDTO(any(Producto.class))).thenReturn(productoDTO);

        Optional<ProductoDTO> resultado = productoService.buscarPorNombre("Laptop");

        assertTrue(resultado.isPresent());
        assertEquals("Laptop", resultado.get().getNombreProducto());
        verify(productoRepository).findByNombreProducto(anyString());
    }

    @Test
    void buscarPorId() {
        when(productoRepository.findByIdProducto(anyLong())).thenReturn(Optional.of(producto));
        when(productoMapper.toDTO(any(Producto.class))).thenReturn(productoDTO);

        Optional<ProductoDTO> resultado = productoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getIdProducto());
        verify(productoRepository).findByIdProducto(anyLong());
    }

    /*@Test
    void actualizarProducto() {
        when(productoRepository.findByIdProducto(anyLong())).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(productoMapper.toDTO(any(Producto.class))).thenReturn(productoDTO);

        ProductoDTO resultado = productoService.actualizarProducto(1L, productoDTO);

        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getNombreProducto());
        verify(productoRepository).save(any(Producto.class));
    }*/

    @Test
    void eliminarProducto() {
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));

        productoService.eliminarProducto(1L);

        verify(productoRepository).deleteById(anyLong());
    }

    @Test
    void cambiarEstadoProducto() {
        // Configuración inicial de mocks
        when(productoRepository.findByIdProducto(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> {
            Producto productoActualizado = invocation.getArgument(0);
            producto.setEstado(productoActualizado.getEstado()); // Actualiza el estado del producto original
            return productoActualizado;
        });
        when(productoMapper.toDTO(any(Producto.class))).thenAnswer(invocation -> {
            Producto productoMock = invocation.getArgument(0);
            productoDTO.setEstado(productoMock.getEstado()); // Refleja el estado actualizado en el DTO
            return productoDTO;
        });

        // Ejecución del método bajo prueba
        ProductoDTO resultado = productoService.cambiarEstadoProducto(1L, EstadoProducto.NO_DISPONIBLE);

        // Verificaciones
        assertNotNull(resultado); // Asegurarse de que el resultado no sea nulo
        assertEquals(EstadoProducto.NO_DISPONIBLE, resultado.getEstado()); // Verificar el cambio de estado
        verify(productoRepository).save(any(Producto.class)); // Verificar que se guardó el producto
        verify(productoMapper).toDTO(any(Producto.class)); // Verificar que se mapeara el producto a DTO
    }

    @Test
    void obtenerProductosPorEstado() {
        when(productoRepository.findByEstado(any(EstadoProducto.class))).thenReturn(Arrays.asList(producto));
        when(productoMapper.toDTO(any(Producto.class))).thenReturn(productoDTO);

        List<ProductoDTO> resultado = productoService.obtenerProductosPorEstado(EstadoProducto.DISPONIBLE);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(productoRepository).findByEstado(any(EstadoProducto.class));
    }

    @Test
    void obtenerProductosPorCategoria() {
        when(productoRepository.findByCategoriaIdCategoria(anyLong())).thenReturn(Arrays.asList(producto));
        when(productoMapper.toDTO(any(Producto.class))).thenReturn(productoDTO);

        List<ProductoDTO> resultado = productoService.obtenerProductosPorCategoria(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(productoRepository).findByCategoriaIdCategoria(anyLong());
    }
}
