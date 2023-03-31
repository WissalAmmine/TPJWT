package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTRSAGenToken {
    public static String createJwtToken(PrivateKey privateKey){
        String token=null;
        Instant now = Instant.now();
        Map<String,String> claims=new HashMap<>();
        claims.put("neme","ahmed");
        claims.put("age","45");
        claims.put("email","ahmed@gmail.com");
        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(2, ChronoUnit.DAYS)))
                .setClaims(claims)
                .signWith(privateKey);
        token=jwtBuilder.compact();
        return token;
    }
    public static Jws<Claims> validateParseToken(String token) throws Exception {
        PublicKey publicKey=CryptoUtils.getPublicKeyFromCertificate("publickeyCertif.cert");
        Jws<Claims> claimsJws1=Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token);
        return claimsJws1;
    }
    public static void main(String[] args) throws Exception {
        PrivateKey privateKey=CryptoUtils.getPrivateKeyFromKeyStore("Wissal.jks","123456789","wissal");
        String token =createJwtToken(privateKey);
        Jws<Claims> claimsJws=validateParseToken(token);
        System.out.println("Header : " + claimsJws.getHeader());
        System.out.println("Body : " + claimsJws.getBody());
        System.out.println("Signature : " + claimsJws.getSignature());

    }
}
