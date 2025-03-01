package com.billsplitter.security;

import org.springframework.stereotype.Component;
import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "your-secret-key";  // Change this to a secure key
    private final long EXPIRATION_TIME = 100L * 365 * 24 * 60 * 60 * 1000; // 100 years

    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    public String generateToken(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public String extractUserId(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            return null; // Invalid token
        }
    }

    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false; // Token is invalid or expired
        }
    }
}
