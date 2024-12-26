package com.tghtechnology.posweb.data.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "categoria")
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "nombre_categoria", nullable = false, length = 50)
    private String nombreCategoria;

    public Categoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}
