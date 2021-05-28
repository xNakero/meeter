package com.example.meeter.service;

import org.springframework.security.core.Authentication;

public interface JwtTokenProviderService {

    String generateToken(Authentication authentication);
}
