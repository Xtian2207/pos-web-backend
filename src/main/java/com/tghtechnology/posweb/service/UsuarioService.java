package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.dto.RolDto;
import com.tghtechnology.posweb.data.dto.UserCreateDTO;
import com.tghtechnology.posweb.data.dto.UsuarioDto;

import java.util.List;
import java.util.Set;




public interface UsuarioService {

    // obtener todos los usuarios
    List<UsuarioDto> obtenerUsuarios();

    // actualizar o agregar nuevo usuario
    void ingresarUsuario(UserCreateDTO user);
    
    void actualizarUsuario(UsuarioDto user);

    // buscar un usuario
    UsuarioDto obtenerUsuarioId(Long idUser);

    // eliminar un usuario
    void eliminarUsuario(Long idUser);

    void cambiarEstadoUsuario(Long id, String estado);

    void cambiarConstrasena(Long id, String pass);

    boolean existeUsuario(Long id);

    Boolean existeUsuarioByCorreo(String correo);

    // roles de un usuario
    Set<RolDto> rolesUsuario(Long id);

    void agregarRol(Long id, Long rol);

    void eliminarRol(Long idUsuario, Long idRol);

}
