package com.example.Login.services.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.Login.dto.responses.JWTClaims;
import com.example.Login.entities.Users;
import com.example.Login.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Objects;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    private final String JWT_SECRET;
    private final String JWT_ISSUE;
    private final Long JWT_EXPIRED_AT;

    public JwtServiceImpl(@Value(value = "${login_with-redis.jwt.secret_key}") String JWT_SECRET,
                          @Value(value = "${login_with-redis.jwt.issuer}") String JWT_ISSUE,
                          @Value(value = "${login_with-redis.jwt.expirationInSecond}") Long JWT_EXPIRED_AT) {
        this.JWT_SECRET = JWT_SECRET;
        this.JWT_ISSUE = JWT_ISSUE;
        this.JWT_EXPIRED_AT = JWT_EXPIRED_AT;
    }

    public String generateToken(Users userAccount) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
            return JWT.create()
                    .withSubject(userAccount.getId().toString())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plusSeconds(JWT_EXPIRED_AT))
                    .withIssuer(JWT_ISSUE)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error: While Creating Token");
        }
    }

    public Boolean verifyToken(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUE)
                    .build();

            verifier.verify(parseJWTToken(token));
            return true;
        } catch (JWTVerificationException exception){
            log.info(exception.getLocalizedMessage());
        }
        return false;
    }

    public JWTClaims claimToken(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUE)
                    .build();

            decodedJWT = verifier.verify(parseJWTToken(token));
            log.info(parseJWTToken(token));
            return JWTClaims.builder()
                    .idUser(Integer.valueOf(decodedJWT.getSubject()))
                    .build();
        } catch (JWTVerificationException exception){
            log.info(exception.getLocalizedMessage());
        }
        return null;
    }

    private String parseJWTToken(String token)
    {
        if (Objects.nonNull(token) && token.startsWith("Bearer "))
        {
            return token.substring(7);
        }
        return null;
    }
}
