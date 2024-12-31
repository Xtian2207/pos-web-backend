package com.tghtechnology.posweb.data.dto;

import java.util.Set;

import com.tghtechnology.posweb.data.entities.Rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    private Long idUsuario;
    private String nombreCompleto;
    private String correo;
    private String estado;
    private Set<RolDto> roles;
}
