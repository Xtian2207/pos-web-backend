package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.dto.RolDto;
import com.tghtechnology.posweb.data.entities.Rol;
import java.util.List;



public interface RolService {

    List<Rol> obtenerRoles();

    void ingresarRol(Rol rol);

    void eliminarRol(Long id);

    Rol editarRol(Long id,Rol rol);

    Rol obtenerRolById(Long id);

    Boolean existeRol(Long id);
}
