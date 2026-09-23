package com.spring_security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
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
}
