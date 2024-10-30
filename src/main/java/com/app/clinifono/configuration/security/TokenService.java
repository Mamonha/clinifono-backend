package com.app.clinifono.configuration.security;

import com.app.clinifono.entities.Usuarios;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("{api.security.token.secret}")
    private String security;

    public String tokenGenerate(Usuarios usuario) {
        try {
            var algorithm = Algorithm.HMAC256(security);
            return JWT.create()
                    .withIssuer("clinifono")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withClaim("nome", usuario.getNome())
                    .withExpiresAt(expiresToken())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String getSubject(String tokenJwt) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(security);
            return  JWT.require(algorithm)
                    .withIssuer("clinifono")
                    .build()
                    .verify(tokenJwt)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token jwt inválido ou expirado");
        }
    }

    private Instant expiresToken() {
            return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
