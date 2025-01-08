package com.tghtechnology.posweb.security;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;
import com.tghtechnology.posweb.service.impl.UsuarioServiceImpl;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario userEntity = userRepository.findByCorreo(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + email));

        System.out.println("usuario encontrado : " + userEntity.getCorreo());

    // Compara la contraseña cifrada
        if (!passwordEncoder.matches("passwordIntroducida", userEntity.getPass())) {
            throw new BadCredentialsException("La contraseña no es válida");
        }

        Set<GrantedAuthority> authorities = userEntity.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getNombreRol()))
            .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
            userEntity.getCorreo(),
            userEntity.getPass(),
            authorities
        );
    }
}