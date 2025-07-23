package com.vegstore.user_service.utils;

import com.vegstore.user_service.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY; // Use strong, secure secret
  
    public JwtUtil() {
//        try{
//            KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk= keyGen.generateKey();
//            SECRET_KEY= Base64.getEncoder().encodeToString(sk.getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
    }

    public String generateToken(User userDetails) {
        return Jwts.builder()

                .setSubject(userDetails.getFullName())
                .claim("email", userDetails.getEmail())
                .claim("fullName", userDetails.getFullName())
                .claim("roles", userDetails.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(getKey())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(getKey()).build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }


    private Key getKey(){
        byte[] ss= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(ss);
    }
}
