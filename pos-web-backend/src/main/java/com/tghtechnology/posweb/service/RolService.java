package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.dto.RolDto;
import com.tghtechnology.posweb.data.entities.Rol;
import java.util.List;
import java.util.Set;



public interface RolService {

    List<RolDto> obtenerRoles();

    void ingresarRol(Rol rol);

    void eliminarRol(Long id);

    Rol editarRol(Long id,Rol rol);

    Rol obtenerRolById(Long id);

    Boolean existeRol(Long id);
}
