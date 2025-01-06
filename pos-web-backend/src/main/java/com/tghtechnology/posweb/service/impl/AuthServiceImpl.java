package com.tghtechnology.posweb.service.impl;

import com.tghtechnology.posweb.data.dto.LoginDto;
import com.tghtechnology.posweb.security.JwtAuthenticationFilter;
import com.tghtechnology.posweb.security.JwtTokenProvider;
import com.tghtechnology.posweb.service.AuthService;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDto loginDto) {
        // Autenticar usuario
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPass())
        );

        // Si la autenticación es exitosa, genera un token
        return jwtTokenProvider.generateToken(authentication);
    }
}
