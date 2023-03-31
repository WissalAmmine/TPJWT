package org.example;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTHMacGenToken {
    public static void main(String[] args) {
        String secret ="R0hKS0xNX0dISktMTThVWUdISktMTV9HSEpLTE04VVk=";
        SecretKey secretKey = new SecretKeySpec(Base64.getUrlDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
        Map<String, String> claims = new HashMap<>();
        claims.put("name", "wissal");
        claims.put("email", "wissal.ammine@gmail.com");
        Instant now =  Instant.now();
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(Date.from(now))
                .setId("123456789")
                .setExpiration(Date.from(now.plus(10, ChronoUnit.MINUTES)))
                .setSubject("jwt authentification")
                .setClaims(claims)
                .signWith(secretKey);
        String jwtToken = jwtBuilder.compact();
        System.out.println(jwtToken);

    }
}
