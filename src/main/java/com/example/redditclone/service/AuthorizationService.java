package com.example.redditclone.service;

import com.example.redditclone.dto.RegisterRequest;
import com.example.redditclone.exception.AuthorizationException;
import com.example.redditclone.exception.UserNotFoundException;
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
    private final EmailService emailService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void singUp(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setEnabled(false);

        userReposiroty.save(user);

        String token = generateVerificationToken(user);
        emailService.sendMail(
                "example@gmail.com",
                user.getEmail(),
                "Please activate your account",
                "http://localhost:8080/api/auth/accountVerification/" + token);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new AuthorizationException("Invalid token"));
        fetchUserAndEnable(verificationToken);
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userReposiroty.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with name " + username + " not found"));
        user.setEnabled(true);
        userReposiroty.save(user);
    }
}
