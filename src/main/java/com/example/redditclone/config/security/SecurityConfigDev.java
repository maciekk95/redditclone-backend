package com.example.redditclone.config.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Profile("dev")
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfigDev {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails user = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .authorities(new SimpleGrantedAuthority("ADMIN"))
                .build();

        manager.createUser(user);
        return manager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth ->
                auth.antMatchers("/api/**").permitAll()
                        .anyRequest().authenticated());

        http.formLogin(form ->
                form.loginPage("/login").permitAll());

        http.logout(logout ->
                logout.logoutUrl("/logout").logoutSuccessUrl("/login"));


        http.csrf(csrf ->
                csrf.disable());

        return http.build();
    }
}