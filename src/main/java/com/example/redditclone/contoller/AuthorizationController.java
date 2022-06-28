package com.example.redditclone.contoller;

import com.example.redditclone.dto.RegisterRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.redditclone.service.AuthorizationService;

@Configuration
@RequestMapping("/api/auth")
public class AuthorizationController {

    AuthorizationService authorizationService;

    @PostMapping("/signup")
    public void singUp(@RequestBody RegisterRequest registerRequest) {
        authorizationService.singUp(registerRequest);
    }
}
