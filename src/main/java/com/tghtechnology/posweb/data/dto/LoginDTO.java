package com.tghtechnology.posweb.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {

    private String correo;
    private String password;
    
}
