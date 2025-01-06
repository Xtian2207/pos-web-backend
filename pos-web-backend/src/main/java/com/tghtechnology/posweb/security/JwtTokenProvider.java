package com.tghtechnology.posweb.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.beans.JavaBean;
import java.security.Key;
import java.util.Date;

@Component
@JavaBean
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private Long jwtExpirationMilliseconds;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        
        Date currenDate = new Date();

        Date expireDate = new Date(currenDate.getTime() + jwtExpirationMilliseconds);
        
        String token = Jwts.builder()
                        .subject(username)
                        .issuedAt(new Date())
                        .expiration(expireDate)
                        .signWith(key())
                        .compact();
        
        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token){
        return Jwts.parser()
                .setSigningKey(key()) // Establece la clave de firma
                .build()
                .parseClaimsJws(token) // Analiza el token JWT
                .getBody()  // Obtiene el cuerpo (claims)
                .getSubject(); // Devuelve el 'subject' que contiene el nombre de usuario
    }
    

    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(key()) // Establece la clave de firma
                    .build()
                    .parseClaimsJws(token); // Verifica el token y lo analiza
            return true; // Si no se lanza excepción, el token es válido
        } catch (ExpiredJwtException e) {
            System.out.println("Token expirado");
            return false; // El token ha expirado
        } catch(Exception e){
            System.out.println("Error en la validación del token");
            return false; // Token inválido o error general
        }
    }

    @PostConstruct
    public void logProperties() {
        System.out.println("JWT Expiration: " + jwtExpirationMilliseconds);
    }

}