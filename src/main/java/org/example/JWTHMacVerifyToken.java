package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class JWTHMacVerifyToken {
    public static void main(String[] args) {
        String token="eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiYWhtZWQiLCJlbWFpbCI6Indpc3NhbC5hbW1pbmVAZ21haWwuY29tIn0.mKQf9dzpSaVH87Y57pvcYitLEkFJweO_Oa2KbKIzK6Y\n";
        String secret ="R0hKS0xNX0dISktMTThVWUdISktMTV9HSEpLTE04VVk";
        SecretKey secretKey = new SecretKeySpec(Base64.getUrlDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
        System.out.println(claimsJws.toString());
    }
}

