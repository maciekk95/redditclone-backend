package com.example.redditclone.service;

import com.example.redditclone.repository.UserReposiroty;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private final UserReposiroty userReposiroty;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userReposiroty.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + " not found."));
    }
}
