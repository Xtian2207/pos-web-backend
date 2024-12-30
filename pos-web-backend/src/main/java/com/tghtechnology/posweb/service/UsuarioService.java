package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.Rol;
import com.tghtechnology.posweb.data.entities.Usuario;

import java.util.List;
import java.util.Set;


public interface UsuarioService {

    // obtener todos los usuarios
    List<Usuario> obtenerUsuarios();

    // actualizar o agregar nuevo usuario
    void ingresarUsuario(Usuario user);
    
    void actualizarUsuario(Usuario user);

    // buscar un usuario
    Usuario obtenerUsuarioId(Long idUser);

    // eliminar un usuario
    void eliminarUsuario(Long idUser);

    void cambiarEstadoUsuario(Long id, String estado);

    void cambiarConstrasena(Long id, String pass);

    boolean existeUsuario(Long id);

    // roles de un usuario
    Set<Rol> rolesUsuario(Long id);

    void agregarRol(Long id, Long rol);

    void eliminarRol(Long idUsuario, Long idRol);
}
