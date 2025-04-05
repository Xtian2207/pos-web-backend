package com.tghtechnology.posweb.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String imagenUrl;

    @NotBlank
    private String imagenId;

    public Imagen(String nombre, String imagenUrl, String imagenId) {
        this.nombre = nombre;
        this.imagenUrl = imagenUrl;
        this.imagenId = imagenId;
    }

}
