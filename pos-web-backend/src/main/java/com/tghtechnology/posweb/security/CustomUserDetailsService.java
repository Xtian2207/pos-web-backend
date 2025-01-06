package com.tghtechnology.posweb.security;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tghtechnology.posweb.data.entities.Usuario;
import com.tghtechnology.posweb.data.repository.UsuarioRepository;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Usuario usuario = usuarioRepository.findByCorreo(email)
        .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe con ese correo"));
        
        Set<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map((rol) -> new SimpleGrantedAuthority(rol.getNombreRol()))
                .collect(Collectors.toSet());
        
        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("vendedor"));
        }

        return new org.springframework.security.core.userdetails.User(
            email,
            usuario.getPass(),
            authorities
        );
    }

}