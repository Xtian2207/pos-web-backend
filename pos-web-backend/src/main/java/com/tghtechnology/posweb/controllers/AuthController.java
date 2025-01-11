package com.tghtechnology.posweb.controllers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tghtechnology.posweb.data.dto.LoginDTO;
import com.tghtechnology.posweb.data.dto.UserCreateDTO;
import com.tghtechnology.posweb.data.repository.RolRepository;
import com.tghtechnology.posweb.security.JWTAuthResonseDto;
import com.tghtechnology.posweb.security.JwtTokenProvider;
import com.tghtechnology.posweb.service.impl.UsuarioServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/iniciarSesion")
    public ResponseEntity<JWTAuthResonseDto> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getCorreo(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResonseDto(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        // Verificar si el correo ya está registrado
        if (usuarioServiceImpl.existeUsuarioByCorreo(userCreateDTO.getCorreo())) {
            return new ResponseEntity<>("Ese correo ya está registrado", HttpStatus.BAD_REQUEST);
        }

        System.out.println("Datos recibidos: " + userCreateDTO);

        usuarioServiceImpl.ingresarUsuario(userCreateDTO);
        return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.OK);
    }

}
