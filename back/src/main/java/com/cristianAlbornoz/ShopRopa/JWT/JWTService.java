package com.cristianAlbornoz.ShopRopa.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class JWTService {
    private String SECRET_KEY = "45774a4a9b3ab452ea91681d20a1ace04168ebde939d88e7ee647eeacf9d487a";

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Objects> extraClaims, UserDetails userDetails) {

        String token = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSingKey(), SignatureAlgorithm.HS256)
                .compact();

        return "Bearer " + token;
    }


    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractSubject(String token) {
        return extractClaims(token).getSubject();
    }

    public Boolean validateToken(UserDetails userDetails, String token) {
        String subject = extractSubject(token);
        return (userDetails.getUsername().equals(subject)) && !tokenExpired(token);
    }

    private Boolean tokenExpired(String token) {
        return (extractClaims(token).getExpiration().before(new Date()));
    }

    private Key getSingKey() {
        byte[] keyByts = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByts);
    }


}
