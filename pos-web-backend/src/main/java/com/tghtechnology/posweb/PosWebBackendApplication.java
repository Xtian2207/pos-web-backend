package com.tghtechnology.posweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.tghtechnology.posweb.data.dto.UserCreateDTO;
import com.tghtechnology.posweb.service.impl.UsuarioServiceImpl;

@SpringBootApplication
public class PosWebBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosWebBackendApplication.class, args);
		
		

	}

}
