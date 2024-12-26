package com.tghtechnology.posweb.data.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "actividad_empleado")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActividadEmpleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Long idActividad;

    @ManyToOne
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_usuario", nullable = false)
    private Usuario empleado;

    @ManyToOne
    @JoinColumn(name = "id_venta", referencedColumnName = "id_venta", nullable = false)
    private Venta venta;


}
