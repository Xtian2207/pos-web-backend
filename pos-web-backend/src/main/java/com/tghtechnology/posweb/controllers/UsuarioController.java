package com.tghtechnology.posweb.controllers;

import java.util.List;
import java.util.Set;

import com.tghtechnology.posweb.data.dto.RolDto;
import com.tghtechnology.posweb.data.dto.UsuarioDto;
import com.tghtechnology.posweb.data.entities.EstadoUsuario;
import com.tghtechnology.posweb.data.entities.Rol;
import com.tghtechnology.posweb.data.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tghtechnology.posweb.service.impl.RolServiceImpl;
import com.tghtechnology.posweb.service.impl.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    private RolServiceImpl rolServiceImpl;

    // lista de usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listaUsuarios(){
        List<UsuarioDto> lista =  usuarioServiceImpl.obtenerUsuarios();
        if (lista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // obtener usuario con su ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obtenerUsuarioId(@PathVariable Long id){
        UsuarioDto usuario = usuarioServiceImpl.obtenerUsuarioId(id);
        if (usuario !=null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // nuevo usuario
    @PostMapping
    public ResponseEntity<String> ingresarUsuario(@RequestBody Usuario usuario){
        try {
            usuarioServiceImpl.ingresarUsuario(usuario);
            return new ResponseEntity<>("Usuario creado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear usuario: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario){
        if(usuarioServiceImpl.obtenerUsuarioId(id) != null){
            usuario.setIdUsuario(id);
            usuarioServiceImpl.actualizarUsuario(usuario);
            return new ResponseEntity<>("Usuario actualizado correctamente", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id){
        if(usuarioServiceImpl.obtenerUsuarioId(id) != null){
            usuarioServiceImpl.eliminarUsuario(id);
            return new ResponseEntity<>("Usuario eliminado", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // Cambiar estado del usuario
    @PatchMapping("/{id}/estado")
    public ResponseEntity<String> cambiarEstadoUsuario(@PathVariable Long id, @RequestParam String estado){
        if(estado.equalsIgnoreCase(EstadoUsuario.ACTIVO.name()) || estado.equalsIgnoreCase(EstadoUsuario.INACTIVO.name())){
            usuarioServiceImpl.cambiarEstadoUsuario(id, estado);
            return new ResponseEntity<>("Estado actualizado del usuario", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Estado invalido.", HttpStatus.BAD_REQUEST);
        }
    }

    // cambiar contraseña
    @PatchMapping("/{id}/contrasena")
    public ResponseEntity<String> cambiarPassUsuario(@PathVariable Long id, @RequestParam String contrasena){
        if (contrasena == null || contrasena.trim().isEmpty()) {
            return new ResponseEntity<>("La contraseña no puede estar vacía", HttpStatus.NOT_FOUND);
        }

        if (!usuarioServiceImpl.existeUsuario(id)) {
            return new ResponseEntity<>("El usuario no existe", HttpStatus.NOT_FOUND);
        }

        usuarioServiceImpl.cambiarConstrasena(id, contrasena);
        return new ResponseEntity<>("Contraseña actualizada correctamente", HttpStatus.OK);
    }

    // Agregar rol a un usuario
    @PostMapping("/{id}/roles/{idRol}")
    ResponseEntity<String> agregarRolUsuario(@PathVariable Long id, @PathVariable Long idRol){
            try {
                if(rolServiceImpl.existeRol(idRol) && usuarioServiceImpl.existeUsuario(id)){
                    usuarioServiceImpl.agregarRol(id, idRol);
                    return new ResponseEntity<>("Rol agregado correctamente al usuario", HttpStatus.OK);
                }else{
                    return new ResponseEntity<>("Uusuario o Rol no existe", HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                return new ResponseEntity<>("Error al agregar nuevo rol al usuario :"+e.getMessage(), HttpStatus.BAD_REQUEST);
            }
    }
        

    // Eliminar un rol de un usuario
    @DeleteMapping("/{id}/roles/{idRol}")
    ResponseEntity<String> eliminarRolUsuario(@PathVariable Long id, @PathVariable Long idRol){
        try {
            if(rolServiceImpl.existeRol(idRol) && usuarioServiceImpl.existeUsuario(id)){                    
                usuarioServiceImpl.eliminarRol(id, idRol);
                return new ResponseEntity<>("Rol agregado correctamente al usuario", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Uusuario o Rol no existe", HttpStatus.BAD_REQUEST);
            }
               
        } catch (Exception e) {
            return new ResponseEntity<>("Error al agregar nuevo rol al usuario :"+e.getMessage(), HttpStatus.BAD_REQUEST);            
        }
    }

    // Roles de un usuario
    @GetMapping("/{id}/roles")
    public ResponseEntity<Set<RolDto>> obtenerRolesUsuario(@PathVariable Long id){
        try {
            if(usuarioServiceImpl.existeUsuario(id)){                    
                Set<RolDto>roles = usuarioServiceImpl.rolesUsuario(id);
                return new ResponseEntity<>(roles , HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
