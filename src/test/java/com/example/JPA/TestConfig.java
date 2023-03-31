package com.example.JPA;
import com.example.JPA.config.ApplicationConfig;
import com.example.JPA.config.JwtService;
import com.example.JPA.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class TestConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public Faker faker(){
        return new Faker();
    }

}