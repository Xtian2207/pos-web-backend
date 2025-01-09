package com.tghtechnology.posweb.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTAuthResonseDto {

    private String tokenDeAcceso;
    private String tipoDeToken = "Bearer";

    public JWTAuthResonseDto(String tokenDeAcceso) {
        super();
        this.tokenDeAcceso = tokenDeAcceso;
    }

}
