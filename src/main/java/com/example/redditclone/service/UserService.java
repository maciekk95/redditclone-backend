package com.example.redditclone.service;

import com.example.redditclone.model.User;
import com.example.redditclone.repository.UserRepository;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
public class UserService  {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + " not found."));
        return mapToUserDetails(user);
    }

    private UserDetails mapToUserDetails(User user) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .disabled(!user.isEnabled())
                .accountExpired(true)
                .credentialsExpired(true)
                .accountLocked(true)
                .authorities(getAuthorities("USER"))
                .build();
        return userDetails;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Sets.newHashSet(new SimpleGrantedAuthority(role));
    }
}
