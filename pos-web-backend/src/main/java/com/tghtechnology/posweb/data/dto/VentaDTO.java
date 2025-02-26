package com.tghtechnology.posweb.data.dto;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class VentaDTO {

    private Long idVenta;
    private Long idUsuario;
    private String nombreUsuario;
    private List<DetalleVentaDTO> detalles; 
    private String metodoPago;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaVenta;
    
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime horaVenta;

    private String tipoDeVenta;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ClienteDTO cliente;
    
    private String nombreCliente; 
    private Double total;

}



