package com.example.digital_wallet.webtoken;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.example.digital_wallet.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET = "43b5df0b85cc6c128288f61383184c4a9b0d701e1242f5e3992029c9cabbeb40";
    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(User user){
        HashMap<String,String> claims = new HashMap<>();
        claims.put("iss", "walletBackend");
        return Jwts.builder()
            .subject(user.getEmail())
            .claims(claims)
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
            .signWith(generateKey())
            .compact();
    }

    private SecretKey generateKey(){
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractEmail(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    private Claims getClaims(String jwt) {
        Claims claims = Jwts.parser()
            .verifyWith(generateKey())
            .build()
            .parseSignedClaims(jwt)
            .getPayload();
        return claims;
    }

    public boolean isTokenValid(String jwt) {
       return getClaims(jwt).getExpiration().after(Date.from(Instant.now()));
    }
}
