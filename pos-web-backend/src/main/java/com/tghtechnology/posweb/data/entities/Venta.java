package com.tghtechnology.posweb.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Table(name = "venta")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long idVenta;

    //@ManyToOne con Usuario: Un usuario (como empleado o cajero) realiza la venta
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    private Usuario usuario;

    //@OneToMany con DetalleVenta: Cada venta puede tener múltiples productos comprados
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetalleVenta> detalles;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "hora_venta")
    private LocalTime horaVenta;

    @Column(name = "fecha_venta")
    private Date fechaVenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "idCliente", nullable = true)
    private Cliente cliente = null;

}
