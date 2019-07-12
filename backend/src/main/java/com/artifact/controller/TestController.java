package com.artifact.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}