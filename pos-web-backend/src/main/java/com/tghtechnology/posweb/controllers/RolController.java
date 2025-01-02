package com.tghtechnology.posweb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.tghtechnology.posweb.data.dto.RolDto;
import com.tghtechnology.posweb.data.entities.Rol;
import com.tghtechnology.posweb.service.impl.RolServiceImpl;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolServiceImpl rolServiceImpl;

    // Obtener lista de roles
    @GetMapping
    public ResponseEntity<List<RolDto>> listaRoles(){
        List<RolDto> lista = rolServiceImpl.obtenerRoles();

        if(lista.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Crear un nuevo rol
    @PostMapping
    public ResponseEntity<String> crearNuevoRol(@RequestBody RolDto rold) {
        if (rold.getNombreRol() == null || rold.getNombreRol().trim().isEmpty()) {
            return new ResponseEntity<>("Error: El nombre del rol no puede estar vacío", HttpStatus.BAD_REQUEST);
        }

        try {
            rolServiceImpl.ingresarRol(rold);
            return new ResponseEntity<>("Rol creado con exito", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: Ocurrió un problema al crear el rol", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar un rol
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRol(@PathVariable Long id){
        try {
            rolServiceImpl.eliminarRol(id);
            return new ResponseEntity<>("Rol eliminado con exito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar rol: "+ e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Editar un rol
    @PutMapping("/{id}")
    public ResponseEntity<Rol> editarRol(@PathVariable Long id, @RequestBody RolDto rolupdate){
        try {
            Rol rol = rolServiceImpl.editarRol(id, rolupdate);
            return new ResponseEntity<>(rol, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
