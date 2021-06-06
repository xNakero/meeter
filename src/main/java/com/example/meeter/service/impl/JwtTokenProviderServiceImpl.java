package com.example.meeter.service.impl;

import com.example.meeter.service.JwtTokenProviderService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenProviderServiceImpl implements JwtTokenProviderService {

    private final int EXPIRE_AFTER = 1000 * 60 * 60 * 24;
    private final String secret = "Lj1xiAOz/D+E{E%";

    @Override
    public String generateToken(Authentication authentication) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRE_AFTER))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
