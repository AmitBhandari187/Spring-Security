package com.spring_security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.authorizeHttpRequests(authorizeRequest->authorizeRequest.anyRequest().authenticated());
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
//    UserDetailsService = To load user details either from DB , In-memory.
//    UserDetails = To represent the data loaded by user detail service.

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1= User.withUsername("user1")
                .password("{noop}pass1")  // {noop} is added so password will stay in normal text and not decoded
                .build();

        return new InMemoryUserDetailsManager(user1);
    }

}
