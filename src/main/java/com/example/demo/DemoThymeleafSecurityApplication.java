package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@SpringBootApplication
public class DemoThymeleafSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoThymeleafSecurityApplication.class, args);
    }
}

