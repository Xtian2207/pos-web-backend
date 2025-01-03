package com.tghtechnology.posweb;

import com.tghtechnology.posweb.service.impl.UsuarioServiceImpl;
import com.tghtechnology.posweb.data.entities.EstadoUsuario;
import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/* 
@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void testCambiarEstadoUsuario_WhenUserExists() {
        // Configuración
        Long userId = 1L;
        Usuario usuarioMock = new Usuario();
        usuarioMock.setIdUsuario(userId);
        usuarioMock.setEstado(EstadoUsuario.ACTIVO);

        when(usuarioRepository.existsById(userId)).thenReturn(true);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuarioMock));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);

        // Ejecución
        usuarioService.cambiarEstadoUsuario(userId, "INACTIVO");

        // Verificación
        verify(usuarioRepository, times(1)).existsById(userId);
        verify(usuarioRepository, times(1)).findById(userId);
        verify(usuarioRepository, times(1)).save(usuarioMock);
        assertEquals(EstadoUsuario.INACTIVO, usuarioMock.getEstado());
    }

    @Test
    void testIngresarUsuario_WhenUserDoesNotExist() {
        // Configuración
        Usuario usuarioMock = new Usuario();
        usuarioMock.setIdUsuario(1L);

        when(usuarioRepository.existsById(usuarioMock.getIdUsuario())).thenReturn(false);
        when(usuarioRepository.save(usuarioMock)).thenReturn(usuarioMock);

        // Ejecución
        usuarioService.ingresarUsuario(usuarioMock);

        // Verificación
        verify(usuarioRepository, times(1)).existsById(usuarioMock.getIdUsuario());
        verify(usuarioRepository, times(1)).save(usuarioMock);
    }

    @Test
    void testObtenerUsuarioId_WhenUserExists() {
        // Configuración
        Long userId = 1L;
        Usuario usuarioMock = new Usuario();
        usuarioMock.setIdUsuario(userId);

        when(usuarioRepository.existsById(userId)).thenReturn(true);
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuarioMock));

        // Ejecución
        Usuario usuario = usuarioService.obtenerUsuarioId(userId);

        // Verificación
        assertNotNull(usuario);
        assertEquals(userId, usuario.getIdUsuario());
        verify(usuarioRepository, times(1)).existsById(userId);
        verify(usuarioRepository, times(1)).findById(userId);
    }
}
*/