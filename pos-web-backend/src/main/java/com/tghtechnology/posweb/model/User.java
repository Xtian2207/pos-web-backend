package com.tghtechnology.posweb.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String correo;
    private String pass;
    private String rol;
    private String status;
}
