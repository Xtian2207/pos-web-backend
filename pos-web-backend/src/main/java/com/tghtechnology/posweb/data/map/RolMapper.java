package com.tghtechnology.posweb.data.map;

import com.tghtechnology.posweb.data.dto.RolDto;
import com.tghtechnology.posweb.data.entities.Rol;

public class RolMapper {

    public static RolDto toDto(Rol rol){

        if (rol == null) {
            return null;
        }

        return new RolDto(
            rol.getIdRol(),
            rol.getNombreRol()
        );
    }
}
