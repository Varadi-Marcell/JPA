package com.example.JPA;

import com.example.JPA.config.ApplicationConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"com.example.JPA.controller", "com.example.JPA.service"})
public class Config {
    @Bean
    public UserDetailsService testUserDetailsService() {
        UserDetails user = User.builder()
                .username("tesztFelhasznalo")
                .password("{noop}tesztJelszo")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

}


