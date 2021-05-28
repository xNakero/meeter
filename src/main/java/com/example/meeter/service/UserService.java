package com.example.meeter.service;

import com.example.meeter.dto.LoginDto;
import com.example.meeter.dto.RegistrationDto;
import com.example.meeter.dto.TokenDto;

public interface UserService {

    TokenDto authenticate(LoginDto loginDto);

    TokenDto extendToken();

    void register(RegistrationDto registrationDto);
}
