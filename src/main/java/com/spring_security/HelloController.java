package com.spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/user/hello")
    public String userHello(){
        return "Hello from user";
    }
    @PreAuthorize("hasRole('ADMIN')")  // it is used to authorize that this endpoint is accessible by admin only
    @GetMapping("/admin/hello")
    public String adminHello(){
        return "Hello from admin";
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello from everyone";
    }

    @PostMapping("/signin")  // We have to make this url public without authentication from security config.
    public String login(@RequestBody LoginRequest loginRequest){
        Authentication authentication;
        try {
            authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserName(),
                            loginRequest.getPassword()
                    )
            );
        }catch (AuthenticationException e){
            e.printStackTrace();
            return "Could not authenticate";
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateTokenFromUsername(userDetails.getUsername());
    }
}
