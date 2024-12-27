package com.tghtechnology.posweb;

import com.tghtechnology.posweb.data.entities.Rol;
import com.tghtechnology.posweb.data.repository.RolRepository;
import org.junit.jupiter.api.Test;
import com.tghtechnology.posweb.service.impl.RolServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RolServiceImplTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolServiceImpl rolService;

    @Test
    void testObtenerRoles_WhenRolesExist() {
        // Configuración
        List<Rol> rolesMock = Arrays.asList(new Rol(1L, "ADMIN"), new Rol(2L, "USER"));
        when(rolRepository.findAll()).thenReturn(rolesMock);

        // Ejecución
        List<Rol> roles = rolService.obtenerRoles();

        // Verificación
        assertNotNull(roles);
        assertEquals(2, roles.size());
        verify(rolRepository, times(1)).findAll();
    }

    @Test
    void testIngresarRol_WhenIdRolIsNotZero() {
        // Configuración
        Rol rol = new Rol(1L, "ADMIN");
        when(rolRepository.save(rol)).thenReturn(rol);

        // Ejecución
        rolService.ingresarRol(rol);

        // Verificación
        verify(rolRepository, times(1)).save(rol);
    }

    @Test
    void testEliminarRol_WhenRolExists() {
        // Configuración
        Long idRol = 1L;
        when(rolRepository.existsById(idRol)).thenReturn(true);
        doNothing().when(rolRepository).deleteById(idRol);

        // Ejecución
        rolService.eliminarRol(idRol);

        // Verificación
        verify(rolRepository, times(1)).existsById(idRol);
        verify(rolRepository, times(1)).deleteById(idRol);
    }
}
