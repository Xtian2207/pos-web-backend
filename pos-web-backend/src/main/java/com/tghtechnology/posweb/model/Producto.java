package com.tghtechnology.posweb.model;

import lombok.Data;

@Data
public class Producto {
    private int idProduct;
    private String name;
    private String category;
    private int amount;
    private double price;
    private String description;
}
