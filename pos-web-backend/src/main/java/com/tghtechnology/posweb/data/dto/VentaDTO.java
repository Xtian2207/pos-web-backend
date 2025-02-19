package com.tghtechnology.posweb.data.dto;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class VentaDTO {

    private Long idVenta;
    private Long idUsuario;
    private String nombreUsuario;
    private List<DetalleVentaDTO> detalles; 
    private String metodoPago;
    private Date fechaVenta;
    private LocalTime horaVenta;

    @JsonIgnore
    private ClienteDTO cliente;
    
    private String nombreCliente; 
    private Double total;

}
