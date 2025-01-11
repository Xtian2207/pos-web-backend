package com.tghtechnology.posweb.controllers;

import com.tghtechnology.posweb.data.entities.Rol;
import com.tghtechnology.posweb.service.impl.RolServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.tghtechnology.posweb.controllers.RolController;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/* 
public class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RolServiceImpl rolServiceImpl;

    @InjectMocks
    private RolController rolController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rolController).build();
    }

    @Test
    public void testListaRoles_Exito() throws Exception {
   
        List<Rol> listaRoles = Arrays.asList(
            new Rol(1L, "ADMIN"), 
            new Rol(2L, "USER")
        );
        when(rolServiceImpl.obtenerRoles()).thenReturn(listaRoles);

        mockMvc.perform(get("/api/roles"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombreRol").value("ADMIN"))
            .andExpect(jsonPath("$[1].nombreRol").value("USER"));
    }

    @Test
    public void testListaRoles_Vacio() throws Exception {

        when(rolServiceImpl.obtenerRoles()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCrearNuevoRol_Exito() throws Exception {

        Rol rol = new Rol(null, "ADMIN");

        doNothing().when(rolServiceImpl).ingresarRol(any(Rol.class));

        mockMvc.perform(post("/api/roles")
            .contentType("application/json")
            .content("{\"nombreRol\": \"ADMIN\"}"))
            .andExpect(status().isCreated())
            .andExpect(content().string("Rol creado con exito"));
    }

    @Test
    public void testCrearNuevoRol_NombreRolVacio() throws Exception {
  
        String rolConNombreVacioJson = "{\"nombreRol\": \"\"}";

    
        mockMvc.perform(post("/api/roles")
            .contentType("application/json")
            .content(rolConNombreVacioJson))
            .andExpect(status().isBadRequest()) 
            .andExpect(content().string("Error: El nombre del rol no puede estar vacío")); 
    }

    @Test
    public void testEliminarRol_Exito() throws Exception {
    
        doNothing().when(rolServiceImpl).eliminarRol(1L);

        mockMvc.perform(delete("/api/roles/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Rol eliminado con exito"));
    }

    @Test
    public void testEliminarRol_Error() throws Exception {
        
        doThrow(new RuntimeException("Error al eliminar el rol")).when(rolServiceImpl).eliminarRol(1L);

        mockMvc.perform(delete("/api/roles/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error al eliminar rol: Error al eliminar el rol"));
    }

    @Test
    public void testEditarRol_Exito() throws Exception {
        
        Rol rol = new Rol(null, "ADMIN");
        when(rolServiceImpl.editarRol(eq(1L), any(Rol.class))).thenReturn(rol);

        mockMvc.perform(put("/api/roles/1")
                .contentType("application/json")
                .content("{\"nombreRol\": \"ADMIN\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreRol").value("ADMIN"));
    }

    @Test
    public void testEditarRol_Error() throws Exception {

        when(rolServiceImpl.editarRol(eq(1L), any(Rol.class))).thenThrow(new RuntimeException("Rol no encontrado"));
        mockMvc.perform(put("/api/roles/1")
                .contentType("application/json")
                .content("{\"nombre\": \"ADMIN\"}"))
                .andExpect(status().isNotFound());
    }
}

*/