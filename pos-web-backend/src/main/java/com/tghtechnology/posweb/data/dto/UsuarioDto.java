package com.tghtechnology.posweb.data.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    
    @NotEmpty
    @Positive
    private Long idUsuario;
    
    @NotBlank
    @Size(min = 3, max = 50, message = "El nombre debe estar entre 3 a 50 caracteres")
    private String nombre;

    @NotBlank
    @Size(min = 3, max = 50, message = "El apellido debe estar entre 3 a 50 caracteres")
    private String apellido;

    @Email
    private String correo;
    
    @NotBlank
    private String estado;
    
    
    private Set<RolDto> roles;
}
