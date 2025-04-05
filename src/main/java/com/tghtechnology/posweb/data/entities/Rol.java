package com.tghtechnology.posweb.data.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Table(name = "rol")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", length = 40)
    private Long idRol;

    
    @NotBlank(message = "El rol no puede estar vacio")
    @Column(name = "nombre_rol", nullable = false, length = 50)
    private String nombreRol;

    @PrePersist
    @PreUpdate
    private void normalizarRol(){
        if(nombreRol !=null){
            nombreRol = nombreRol.toUpperCase();
        }
    }

}

