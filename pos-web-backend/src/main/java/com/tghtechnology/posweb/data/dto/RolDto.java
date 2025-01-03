package com.tghtechnology.posweb.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolDto {

    private Long idRol;

    @NotBlank(message = "El nombre del rol no puede estar vacio")
    @Size(min = 3, max = 50, message = "El rol debe tener como minimo 3 caracteres")
    private String nombreRol;
}
