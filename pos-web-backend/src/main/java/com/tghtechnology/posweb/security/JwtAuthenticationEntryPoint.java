package com.tghtechnology.posweb.security;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import jakarta.servlet.ServletException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, 
                        HttpServletResponse response, 
                        AuthenticationException authenticationException) throws IOException, ServletException 
    {
        response.sendError(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
    }
}
