package com.artifact.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping
    public ResponseEntity getHome() {
        return ResponseEntity.ok("Welcome to Your home page");
    }

    @GetMapping("onlyforadmin/welcome")
    public ResponseEntity getSecuredAdmin() {
        return ResponseEntity.ok("Welcome to the secured page. Only for Admin");
    }

    @GetMapping("secured/welcome")
    public ResponseEntity getSecured() {
        return ResponseEntity.ok("Welcome to the secured page");
    }

    @PostMapping("secured/postdata")
    public ResponseEntity postData() {
        return ResponseEntity.ok("Data is saved");
    }

    @GetMapping("logout")
    public ResponseEntity logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){

            new SecurityContextLogoutHandler().logout(request, response, auth);
            return ResponseEntity.ok("logout success");
        }
        return ResponseEntity.ok("auth is null");//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
}