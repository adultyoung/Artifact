package com.artifact.controller;

import com.artifact.dao.UserDetailsDao;
import com.artifact.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    UserDetailsDao dao;

    @GetMapping
    private ResponseEntity registration(@RequestBody User user) {

        if
            (
                    !user.getEmail().isEmpty() && !user.getUsername().isEmpty() && !user.getPassword().isEmpty() &&
                            (user.getUsername().length()<50 && user.getPassword().length()<50 && user.getEmail().length()<50)
        ) {
            dao.save(user);
            return ResponseEntity.ok("Successfully registrated");
        }
        return (ResponseEntity) ResponseEntity.badRequest();
    }
}
