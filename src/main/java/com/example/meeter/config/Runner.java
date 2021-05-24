package com.example.meeter.config;

import com.example.meeter.entity.User;
import com.example.meeter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Runner implements CommandLineRunner {

    private final UserRepository userRepository;

    @Autowired
    public Runner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setEnabled(true);
        user.setUsername("user");
        user.setPassword(new BCryptPasswordEncoder(10).encode("password"));
        userRepository.save(user);
    }
}
