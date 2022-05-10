package com.kula.growthchannelapi.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;


@Component
public class JwtHelper {

    @Value("${jwt.key}")
    private String key;

    public  String create(String id, String subject, Map<String, Object> claims, long ttl) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMills = System.currentTimeMillis();

        Date now = new Date(nowMills);

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);

        if (ttl >= 0) {
            long expMills = nowMills + ttl;
            Date exp = new Date(expMills);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public  Claims parse(String jwt) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody();
    }


}
