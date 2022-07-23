package com.example.redditclone.service;

import com.example.redditclone.dto.RegisterDto;
import com.example.redditclone.exception.AuthorizationException;
import com.example.redditclone.exception.ResourceNotFoundException;
import com.example.redditclone.model.VerificationToken;
import com.example.redditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import com.example.redditclone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.redditclone.repository.UserRepository;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthorizationService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private final EmailService emailService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void singUp(RegisterDto registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .created(Date.from(Instant.now()))
                .enabled(true)
                .build();

        userRepository.save(user);

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
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User with name " + username + " not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User with name " + username + " not found"));
    }
}
