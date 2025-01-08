package com.tghtechnology.posweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tghtechnology.posweb.data.dto.UserCreateDTO;
import com.tghtechnology.posweb.service.impl.UsuarioServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

/* 
@SpringBootApplication
public class MiAplicacion implements CommandLineRunner {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    public static void main(String[] args) {
        SpringApplication.run(MiAplicacion.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO(
            "Angel", "Palomino", "angel@ejemplo.com", "qwleih12oeh11293u912!", "ACTIVO"
        );
        usuarioServiceImpl.ingresarUsuario(userCreateDTO);
    }
}*/