package com.example.redditclone.contoller;

import com.example.redditclone.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.redditclone.service.AuthorizationService;

@Configuration
@RequestMapping("/api/auth")
public class AuthorizationController {

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/signup")
    public ResponseEntity<String> singUp(@RequestBody RegisterRequest registerRequest) {
        authorizationService.singUp(registerRequest);
        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }
}
