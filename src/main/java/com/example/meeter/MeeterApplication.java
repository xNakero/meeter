package com.example.meeter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class MeeterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeeterApplication.class, args);
    }

}
