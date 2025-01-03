package com.tghtechnology.posweb.services;

import com.tghtechnology.posweb.data.entities.Categoria;
import com.tghtechnology.posweb.data.repository.CategoriaRepository;
import com.tghtechnology.posweb.service.impl.CategoriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    private Categoria categoriaMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //Inicializa los mocks
        categoriaMock = new Categoria();
        categoriaMock.setIdCategoria(1L);
        categoriaMock.setNombreCategoria("Electrónica");
    }

    /* 
    @Test
    void crearCategoria_exitoso() {
        // Configurar el comportamiento del mock
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaMock);

        // Llamar al metodo que estamos probando
        Categoria result = categoriaService.crearCategoria(categoriaMock);

        // Aserciones
        assertNotNull(result);
        assertEquals(1L, result.getIdCategoria());
        assertEquals("Electrónica", result.getNombreCategoria());

        // Verificar que se haya llamado al repositorio
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void obtenerTodasLasCategorias_exitoso() {
        // Configurar el comportamiento del mock
        when(categoriaRepository.findAll()).thenReturn(List.of(categoriaMock));

        // Llamar al metodo que estamos probando
        List<Categoria> result = categoriaService.obtenerTodasLasCategorias();

        // Aserciones
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Electrónica", result.get(0).getNombreCategoria());

        // Verificar que se haya llamado al repositorio
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    void obtenerCategoriaPorId_exitoso() {
        // Configurar el comportamiento del mock
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaMock));

        // Llamar al metodo que estamos probando
        Optional<Categoria> result = categoriaService.obtenerCategoriaPorId(1L);

        // Aserciones
        assertTrue(result.isPresent());
        assertEquals("Electrónica", result.get().getNombreCategoria());

        // Verificar que se haya llamado al repositorio
        verify(categoriaRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerCategoriaPorId_noEncontrada() {
        // Configurar el comportamiento del mock
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamar al metodo que estamos probando
        Optional<Categoria> result = categoriaService.obtenerCategoriaPorId(1L);

        // Aserciones
        assertFalse(result.isPresent());

        // Verificar que se haya llamado al repositorio
        verify(categoriaRepository, times(1)).findById(1L);
    }

    @Test
    void actualizarCategoria_exitoso() {
        // Crear la categoria actualizada
        Categoria categoriaActualizada = new Categoria();
        categoriaActualizada.setNombreCategoria("Tecnología");

        // Configurar el comportamiento del mock
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaMock));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaMock);

        // Llamar al metodo que estamos probando
        Categoria result = categoriaService.actualizarCategoria(1L, categoriaActualizada);

        // Aserciones
        assertNotNull(result);
        assertEquals("Tecnología", result.getNombreCategoria());

        // Verificar que se haya llamado al repositorio
        verify(categoriaRepository, times(1)).findById(1L);
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void actualizarCategoria_noEncontrada() {
        // Crear la categoria actualizada
        Categoria categoriaActualizada = new Categoria();
        categoriaActualizada.setNombreCategoria("Tecnología");

        // Configurar el comportamiento del mock para cuando no se encuentre la categoria
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamar al metodo que estamos probando
        Categoria result = categoriaService.actualizarCategoria(1L, categoriaActualizada);

        // Aserciones
        assertNull(result);

        // Verificar que no se haya guardado ninguna categoría
        verify(categoriaRepository, times(1)).findById(1L);
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void eliminarCategoria_exitoso() {
        // Llamar al metodo que estamos probando
        categoriaService.eliminarCategoria(1L);

        // Verificar que se haya llamado al repositorio
        verify(categoriaRepository, times(1)).deleteById(1L);
    }
    */
}
