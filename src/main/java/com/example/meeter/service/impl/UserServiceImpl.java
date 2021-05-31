package com.example.meeter.service.impl;

import com.example.meeter.config.AppPasswordEncoder;
import com.example.meeter.dto.LoginDto;
import com.example.meeter.dto.RegistrationDto;
import com.example.meeter.dto.TokenDto;
import com.example.meeter.entity.RegistrationCode;
import com.example.meeter.entity.User;
import com.example.meeter.exceptions.ConflictException;
import com.example.meeter.exceptions.NotFoundException;
import com.example.meeter.repository.RegistrationCodeRepository;
import com.example.meeter.repository.UserRepository;
import com.example.meeter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProviderServiceImpl jwtTokenProviderService;
    private final RegistrationCodeRepository registrationCodeRepository;
    private final UserRepository userRepository;
    private final AppPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager,
                           JwtTokenProviderServiceImpl jwtTokenProviderService,
                           RegistrationCodeRepository registrationCodeRepository,
                           UserRepository userRepository, AppPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProviderService = jwtTokenProviderService;
        this.registrationCodeRepository = registrationCodeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public TokenDto authenticate(LoginDto loginDto) {
        Authentication userPassAuthentication =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(userPassAuthentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(jwtTokenProviderService.generateToken(authentication));
        return tokenDto;
    }

    @Override
    public TokenDto extendToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(jwtTokenProviderService.generateToken(authentication));
        return tokenDto;
    }

    @Override
    @Transactional
    public void register(RegistrationDto registrationDto) {
        RegistrationCode code = registrationCodeRepository.findByCode(registrationDto.getCode())
                .orElseThrow(() -> new NotFoundException("No such code"));
        Optional<User> userOptional = userRepository.findByUsername(registrationDto.getUsername());
        if (userOptional.isPresent()) {
            throw new ConflictException("User exists");
        }
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setPassword(passwordEncoder.passwordEncoder().encode(registrationDto.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        registrationCodeRepository.delete(code);
    }
}
