package com.tghtechnology.posweb.model;

import lombok.Data;

@Data
public class User {
    private int idUser;
    private String correo;
    private String name;
    private String lastname;
    private String pass;
    private String rol;
    private String status;
}
