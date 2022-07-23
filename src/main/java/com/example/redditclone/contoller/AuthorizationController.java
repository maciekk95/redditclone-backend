package com.example.redditclone.contoller;

import com.example.redditclone.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.redditclone.service.AuthorizationService;

@Configuration
@RequestMapping("/api/auth")
public class AuthorizationController {

    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/signup")
    public ResponseEntity<String> singUp(@RequestBody RegisterDto registerRequest) {
        authorizationService.singUp(registerRequest);
        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authorizationService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }
}
