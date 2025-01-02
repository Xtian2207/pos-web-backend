package com.tghtechnology.posweb.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    private String nombre;
    private String apellido;
    private String correo;
    private String pass;
    private String estado;
}
