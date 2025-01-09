package com.tghtechnology.posweb.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String mensaje){
        super(mensaje);
    }
}
