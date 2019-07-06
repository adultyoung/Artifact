package com.artifact.controller;

import com.artifact.dao.UserDetailsDao;
import com.artifact.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserDetailsDao userDao;
    private PasswordEncoder encoder;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.save(user);
    }
}
