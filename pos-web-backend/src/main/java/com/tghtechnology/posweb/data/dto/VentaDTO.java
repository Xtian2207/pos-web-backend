package com.tghtechnology.posweb.data.dto;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tghtechnology.posweb.data.entities.Cliente;

import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class VentaDTO {

    private Long idVenta;
    private Long idUsuario; 
    private List<DetalleVentaDTO> detalles; 
    private String metodoPago;
    private Date fechaVenta;
    private LocalTime horaVenta;
    private Cliente cliente;

}
