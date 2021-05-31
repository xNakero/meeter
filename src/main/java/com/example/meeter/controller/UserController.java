package com.example.meeter.controller;

import com.example.meeter.dto.LoginDto;
import com.example.meeter.dto.RegistrationDto;
import com.example.meeter.dto.TokenDto;
import com.example.meeter.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto dto) {
        TokenDto tokenDto = userService.authenticate(dto);
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegistrationDto dto) {
        userService.register(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("token-extension")
    public ResponseEntity<?> extendToken() {
        TokenDto tokenDto = userService.extendToken();
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }
}
