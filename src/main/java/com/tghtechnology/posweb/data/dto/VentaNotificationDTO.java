package com.tghtechnology.posweb.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaNotificationDTO {

    private String titulo;
    private Long idVenta;
    private String total;
    private String usuario;
    private String fecha;
}