package com.tghtechnology.posweb.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String estado;
    private String nombreRol;

}
