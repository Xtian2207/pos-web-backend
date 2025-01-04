package com.tghtechnology.posweb.data.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 3, max = 50, message = "El nombre debe estar entre 3 a 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    @Size(min = 3, max = 50, message = "El apellido debe estar entre 3 a 50 caracteres")
    private String apellido;
    
    @Email(message = "El correo debe respetar el formato")
    private String correo;
    
    @NotNull(message = "La contraseña no puede ser nula")
    @Size(min = 8, max = 60, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=]).{8,}$", message = "La contraseña debe contener al menos una letra mayúscula, una minúscula, un número y un carácter especial")
    private String pass;
    
    @NotBlank(message = "Tiene que ser uno de los estados definidos -- ACTIVO O INACTIVO")
    private String estado;
}
