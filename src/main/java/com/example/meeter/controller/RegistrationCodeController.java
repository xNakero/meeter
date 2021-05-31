package com.example.meeter.controller;

import com.example.meeter.entity.RegistrationCode;
import com.example.meeter.service.impl.RegistrationCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class RegistrationCodeController {

    private final RegistrationCodeServiceImpl registrationCodeService;

    @Autowired
    public RegistrationCodeController(RegistrationCodeServiceImpl registrationCodeService) {
        this.registrationCodeService = registrationCodeService;
    }

    @PostMapping("code-generator")
    public ResponseEntity<RegistrationCode> generateCode() {
        return new ResponseEntity<>(registrationCodeService.generateKey(), HttpStatus.CREATED);
    }
}
