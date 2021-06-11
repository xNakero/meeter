package com.example.meeter.service.impl;

import com.example.meeter.entity.RegistrationCode;
import com.example.meeter.exceptions.InternalServerErrorException;
import com.example.meeter.repository.RegistrationCodeRepository;
import com.example.meeter.service.RegistrationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationCodeServiceImpl implements RegistrationCodeService {

    private final RegistrationCodeRepository registrationCodeRepository;

    @Autowired
    public RegistrationCodeServiceImpl(RegistrationCodeRepository registrationCodeRepository) {
        this.registrationCodeRepository = registrationCodeRepository;
    }

    @Override
    public RegistrationCode generateKey() {
        for (int i = 0; i < 5; i++) {
            String code = UUID.randomUUID().toString();
            Optional<RegistrationCode> registrationCodeOptional = registrationCodeRepository.findByCode(code);
            if (registrationCodeOptional.isEmpty()) {
                RegistrationCode registrationCode = new RegistrationCode(code);
                registrationCodeRepository.save(registrationCode);
                return registrationCode;
            }
        }
        throw new InternalServerErrorException("Error generating key. Try again later.");
    }
}
