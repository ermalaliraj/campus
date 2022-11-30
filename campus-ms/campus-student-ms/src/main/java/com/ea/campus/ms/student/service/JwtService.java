package com.ea.campus.ms.student.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@NoArgsConstructor
public class JwtService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Value("${client.key.public}")
    private String publicKey;
    @Value("${client.key.private}")
    private String privateKey;

    public String generateJwtToken() {
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            Map<String, Object> headers = new HashMap<>();
            headers.put("alg", "HS256");

            JWTCreator.Builder builder = JWT.create()
                    .withHeader(headers)
                    .withClaim("sub", publicKey);
            return builder.sign(algorithm);
        } catch (Exception ex) {
            logger.trace("Failed to parse token", ex);
            throw new IllegalArgumentException("Failed to parse token", ex);
        }
    }

    public Jwt verifyJwtToken(String token) {
        try {
            DecodedJWT decodedJWT = com.auth0.jwt.JWT.require(Algorithm.HMAC256(privateKey))
                    .withClaim("sub", publicKey)
                    .build()
                    .verify(token);
            logger.debug("Token verified using HMAC256 algorithm");

            Map<String, Object> headers = new HashMap<>();
            headers.put("alg", "HS256");
            Map<String, Object> claims = new HashMap<>();
            claims.put("sub", decodedJWT.getPayload());

            Jwt jwt = new Jwt(token, null, null, headers, claims);
            return jwt;
        } catch (Exception e) {
            logger.error("Failed to parse and verify JWT token", e);
            throw new IllegalArgumentException("Failed to parse and verify JWT token", e);
        }
    }

}
