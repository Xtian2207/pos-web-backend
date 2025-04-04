package com.tghtechnology.posweb;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.tghtechnology.posweb.data.dto.RolDto;
import com.tghtechnology.posweb.data.dto.UserCreateDTO;
import com.tghtechnology.posweb.data.entities.EstadoUsuario;
import com.tghtechnology.posweb.service.RolService;
import com.tghtechnology.posweb.service.UsuarioService;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializeRoles();
        initializeAdminUser();
    }

    private void initializeRoles() {
        if (!rolService.existeRolName("ADMIN")) {
            RolDto adminRole = new RolDto();
            adminRole.setNombreRol("ADMIN");
            rolService.ingresarRol(adminRole);
        }

        if (!rolService.existeRolName("EMPLEADO")) {
            RolDto userRole = new RolDto();
            userRole.setNombreRol("EMPLEADO");
            rolService.ingresarRol(userRole);
        }
    }

    private void initializeAdminUser() {
        if (!usuarioService.existeUsuarioByCorreo("admin@pos.com")) {
            UserCreateDTO adminDto = new UserCreateDTO();
            adminDto.setNombre("Administrador");
            adminDto.setApellido("Del Sistema");
            adminDto.setCorreo("admin@pos.com");
            adminDto.setPass("$Admin123!");
            adminDto.setEstado(String.valueOf(EstadoUsuario.ACTIVO));

            RolDto adminRole = rolService.obtenerRolByName("ADMIN");
            Set<RolDto> roles = new HashSet<>();
            roles.add(adminRole);
            adminDto.setRoles(roles);

            usuarioService.ingresarUsuario(adminDto);
        }
    }
}
