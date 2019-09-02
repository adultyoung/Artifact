package com.artifact.controller;

import com.artifact.dao.UserDetailsDao;
import com.artifact.domain.User;
import com.artifact.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    CustomUserDetailsService service;

    @Autowired
    UserDetailsDao dao;

    @Autowired
    BCryptPasswordEncoder encoder;

    @PostMapping
    private ResponseEntity registration(
            @RequestBody User user) {

        if
        (
                !user.getEmail().isEmpty() && !user.getUsername().isEmpty() && !user.getPassword().isEmpty() &&
                        (user.getUsername().length() < 50 && user.getPassword().length() < 50 && user.getEmail().length() < 50)
        ) {
            user.setId(UUID.randomUUID().toString());
            user.setPassword(encoder.encode(user.getPassword()));
            user.setLastVisit(LocalDateTime.now());
            user.setActivationCode(UUID.randomUUID().toString());
            dao.save(user);
            service.sendMessage(user);
            return ResponseEntity.ok("Successfully registrated");
        }
        return (ResponseEntity) ResponseEntity.badRequest();
    }
}
