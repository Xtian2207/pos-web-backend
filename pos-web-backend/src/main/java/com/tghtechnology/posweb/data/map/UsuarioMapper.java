package com.tghtechnology.posweb.data.map;

import com.tghtechnology.posweb.data.entities.Usuario;

import java.util.Set;

import com.tghtechnology.posweb.data.dto.RolDto;
import com.tghtechnology.posweb.data.dto.UsuarioDto;
import java.util.stream.Collectors;

public class UsuarioMapper {
    public static UsuarioDto toDTO(Usuario usuario){
        
        if (usuario == null) {
            return null;
        }

        Set<RolDto> rolesDto = usuario.getRoles().stream()
                                .map(rol -> new RolDto(rol.getIdRol(), rol.getNombreRol()))
                                .collect(Collectors.toSet());

        
        return new UsuarioDto(
            usuario.getIdUsuario(),
            usuario.getNombre()+ " "+ usuario.getApellido(),
            usuario.getCorreo(),
            usuario.getEstado() != null ? usuario.getEstado().name() : null,
            rolesDto
        );
    }
}
