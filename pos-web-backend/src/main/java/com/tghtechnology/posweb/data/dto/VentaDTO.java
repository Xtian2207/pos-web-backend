package com.tghtechnology.posweb.data.dto;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;


import lombok.Data;

@Data
public class VentaDTO {

    private Long idVenta;
    private Long idUsuario; 
    private List<DetalleVentaDTO> detalles; 
    private String metodoPago;
    private Date fechaVenta;
    private LocalTime horaVenta;

}
