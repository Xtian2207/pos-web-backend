package com.tghtechnology.posweb.data.entities;

public enum TipoDocumento {
    DNI("DOCUMENTO NACIONAL DE IDENTIDAD"), 
    RUC10("REGISTRO UNICO DE CONTRIBUYENTES - PERSONA NATURAL"), 
    RUC20("REGISTRO UNICO DE CONTRIBUYENTES - PERSONA JURIDICA"), 
    CARNET_EXTRANJERIA("CARNET DE EXTRANJERIA"),
    PASAPORTE("PASAPORTE"),
    CEDULA_DIPLOMATICA("DECULA DIPLOMATICA DE IDENTIDAD");

    private final String descripcion;

    TipoDocumento(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return descripcion;
    }

}
