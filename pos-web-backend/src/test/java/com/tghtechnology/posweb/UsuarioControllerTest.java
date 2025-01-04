package com.tghtechnology.posweb;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import com.tghtechnology.posweb.controllers.UsuarioController;
import com.tghtechnology.posweb.service.impl.UsuarioServiceImpl;
import com.tghtechnology.posweb.data.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
/* 
public class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioServiceImpl usuarioServiceImpl;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    public void setUp() {
        // Inicializa los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    public void testIngresarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        // El ID será generado automáticamente, no es necesario establecerlo

        // Simula la creación del usuario con éxito
        doNothing().when(usuarioServiceImpl).ingresarUsuario(any(Usuario.class));

        mockMvc.perform(post("/api/usuarios")
                .contentType("application/json")
                .content("{ \"nombre\": \"John Doe\" }"))  // No se incluye el id, ya que se genera automáticamente
            .andExpect(status().isCreated()) // Estado 201 Created
            .andExpect(content().string("Usuario creado correctamente")); // Mensaje consistente
        
        // Simula un error al crear usuario
        doThrow(new RuntimeException("Error al crear usuario")).when(usuarioServiceImpl).ingresarUsuario(any(Usuario.class));

        mockMvc.perform(post("/api/usuarios")
                .contentType("application/json")
                .content("{ \"nombre\": \"John Doe\" }"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Error al crear usuario: Error al crear usuario")); // Mensaje de error consistente
    }

    // Prueba para obtener la lista de usuarios
    @Test
    public void testListaUsuarios() throws Exception {
        // Caso con usuarios
        List<Usuario> usuarios = Arrays.asList(new Usuario(), new Usuario());
        when(usuarioServiceImpl.obtenerUsuarios()).thenReturn(usuarios);

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk());
        
        // Caso sin usuarios
        when(usuarioServiceImpl.obtenerUsuarios()).thenReturn(Arrays.asList());
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isNoContent());
    }

    // Prueba para obtener un usuario por ID
    @Test
    public void testObtenerUsuarioId() throws Exception {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        when(usuarioServiceImpl.obtenerUsuarioId(id)).thenReturn(usuario);

        mockMvc.perform(get("/api/usuarios/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(id));
        
        when(usuarioServiceImpl.obtenerUsuarioId(id)).thenReturn(null);
        mockMvc.perform(get("/api/usuarios/{id}", id))
                .andExpect(status().isNotFound());
    }

    // Prueba para actualizar un usuario
    @Test
    public void testActualizarUsuario() throws Exception {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);

        when(usuarioServiceImpl.obtenerUsuarioId(id)).thenReturn(usuario);
        doNothing().when(usuarioServiceImpl).actualizarUsuario(any(Usuario.class));

        mockMvc.perform(put("/api/usuarios/{id}", id)
                .contentType("application/json")
                .content("{ \"nombre\": \"John Updated\" }"))
            .andExpect(status().isOk())
            .andExpect(content().string("Usuario actualizado correctamente"));
        
        when(usuarioServiceImpl.obtenerUsuarioId(id)).thenReturn(null);
        mockMvc.perform(put("/api/usuarios/{id}", id)
                .contentType("application/json")
                .content("{ \"nombre\": \"John Updated\" }"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Usuario no encontrado"));
    }

    // Prueba para cambiar el estado de un usuario
    @Test
    public void testCambiarEstadoUsuario() throws Exception {
        Long id = 1L;

        when(usuarioServiceImpl.obtenerUsuarioId(id)).thenReturn(new Usuario());

        mockMvc.perform(patch("/api/usuarios/{id}/estado", id)
                .param("estado", "ACTIVO"))
            .andExpect(status().isOk())
            .andExpect(content().string("Estado actualizado del usuario"));

        mockMvc.perform(patch("/api/usuarios/{id}/estado", id)
                .param("estado", "INACTIVO"))
            .andExpect(status().isOk())
            .andExpect(content().string("Estado actualizado del usuario"));

        mockMvc.perform(patch("/api/usuarios/{id}/estado", id)
                .param("estado", "INVALIDO"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Estado invalido."));
    }

    // Prueba para cambiar la contraseña de un usuario
    @Test
    public void testCambiarPassUsuario() throws Exception {
        Long id = 1L;

        when(usuarioServiceImpl.existeUsuario(id)).thenReturn(true);

        mockMvc.perform(patch("/api/usuarios/{id}/contrasena", id)
                .param("contrasena", "newpassword123"))
            .andExpect(status().isOk())
            .andExpect(content().string("Contraseña actualizada correctamente"));

        when(usuarioServiceImpl.existeUsuario(id)).thenReturn(false);
        mockMvc.perform(patch("/api/usuarios/{id}/contrasena", id)
                .param("contrasena", ""))
            .andExpect(status().isNotFound())
            .andExpect(content().string("La contraseña no puede estar vacía"));

        mockMvc.perform(patch("/api/usuarios/{id}/contrasena", id)
                .param("contrasena", "ofgijhiodfghd"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("El usuario no existe"));
    }

    // Prueba para eliminar un usuario
    @Test
    public void testEliminarUsuario() throws Exception {
        Long id = 1L;

        when(usuarioServiceImpl.obtenerUsuarioId(id)).thenReturn(new Usuario());
        doNothing().when(usuarioServiceImpl).eliminarUsuario(id);

        mockMvc.perform(delete("/api/usuarios/{id}", id))
            .andExpect(status().isOk())
            .andExpect(content().string("Usuario eliminado"));

        when(usuarioServiceImpl.obtenerUsuarioId(id)).thenReturn(null);
        mockMvc.perform(delete("/api/usuarios/{id}", id))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Usuario no encontrado"));
    }
}
*/