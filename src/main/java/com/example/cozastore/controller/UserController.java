package com.example.cozastore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/signin")
    public ResponseEntity<?>signin(){
        return new ResponseEntity<>("Sign in", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(){
        return new ResponseEntity<>("Sign up", HttpStatus.OK);
    }
}
