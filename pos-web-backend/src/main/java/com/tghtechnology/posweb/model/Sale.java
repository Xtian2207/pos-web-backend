package com.tghtechnology.posweb.model;

import lombok.Data;

@Data
public class Sale {
    private int idVenta;
    private int idUser;
    private double total;
    private String fecha;
    private String hora;
    private String metodoPago;
}
