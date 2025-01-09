package com.tghtechnology.posweb;

import java.security.Key;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class JwtSecretGenerator {
    public static void main(String[] args) {
        
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Algoritmo HMAC-SHA256
        String base64Key = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("Clave segura generada (Base64): " + base64Key);

    }
}
