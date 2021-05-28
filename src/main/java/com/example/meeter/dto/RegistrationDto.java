package com.example.meeter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegistrationDto {

    private String code;
    private String username;
    private String password;
}
