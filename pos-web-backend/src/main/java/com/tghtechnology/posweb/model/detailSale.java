package com.tghtechnology.posweb.model;

import lombok.Data;

@Data
public class detailSale {
    private int idDetail;
    private int idSale;
    private int idProduct;
    private int amount;
    private double price;
    private double subtotal;
}
