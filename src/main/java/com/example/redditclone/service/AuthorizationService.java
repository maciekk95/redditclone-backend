package com.example.redditclone.service;

import com.example.redditclone.dto.RegisterRequest;
import com.example.redditclone.model.VerificationToken;
import com.example.redditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import com.example.redditclone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.redditclone.repository.UserReposiroty;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthorizationService {

    @Autowired
    private final UserReposiroty userReposiroty;

    @Autowired
    private final VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void singUp(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());

        userReposiroty.save(user);

        String token = generateVerificationToken(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

}
