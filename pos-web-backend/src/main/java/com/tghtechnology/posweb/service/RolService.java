package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.entities.Rol;

import java.util.List;
import java.util.Optional;



public interface RolService {

    Optional<List<Rol>> obtenerRoles();

    void ingresarRol(Rol rol);

    void eliminarRol(Long id);

    void editarRol(Rol rol);

    Rol obtenerRolById(Long id);

}
