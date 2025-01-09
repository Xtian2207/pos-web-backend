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
import com.tghtechnology.posweb.data.entities.EstadoUsuario;
import com.tghtechnology.posweb.data.entities.Rol;
import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.repository.RolRepository;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import com.tghtechnology.posweb.security.JWTAuthResonseDto;
import com.tghtechnology.posweb.security.JwtTokenProvider;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        if (usuarioRepository.existsByCorreo(userCreateDTO.getCorreo())) {
            return new ResponseEntity<>("Ese correo ya está registrado", HttpStatus.BAD_REQUEST);
        }

        System.out.println("Datos recibidos: " + userCreateDTO);

        // Crear nueva instancia de Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(userCreateDTO.getNombre());
        usuario.setApellido(userCreateDTO.getApellido());
        usuario.setCorreo(userCreateDTO.getCorreo());
        usuario.setPass(passwordEncoder.encode(userCreateDTO.getPass()));
        usuario.setEstado(EstadoUsuario.valueOf(userCreateDTO.getEstado().toUpperCase()));

        // Asignar roles usando el ID
        Set<Rol> roles = userCreateDTO.getRoles().stream()
                .map(rolDto -> rolRepository.findById(rolDto.getIdRol())
                        .orElseThrow(() -> new RuntimeException("El rol no existe: " + rolDto.getIdRol())))
                .collect(Collectors.toSet());
        usuario.setRoles(roles);

        usuarioRepository.save(usuario);
        return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.OK);
    }

}
