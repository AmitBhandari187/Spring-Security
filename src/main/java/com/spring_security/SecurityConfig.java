package com.spring_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity //to enable security in method level - Go to controller for checking more
public class SecurityConfig {

    @Autowired
    DataSource dataSource; // sql automatically configure the database object here

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.authorizeHttpRequests(authorizeRequest->
                authorizeRequest.requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated());
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
//    UserDetailsService = To load user details either from DB , In-memory.
//    UserDetails = To represent the data loaded by user detail service.

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
        UserDetails user1= User.withUsername("user1")
               // .password("{noop}pass1")  // {noop} is added so password will stay in normal text and not decoded
                .password(passwordEncoder().encode("pass1")) // to save password in encoded way
                .roles("USER")
                .build();

        UserDetails user2 = User.withUsername("user2")
//                .password("{noop}pass2")
                .password(passwordEncoder().encode("pass2")) // to save password in encoded way
                .roles("USER")
                .build();

        UserDetails admin= User.withUsername("admin")
                .roles("ADMIN")
//                .password("{noop}adminPass")
                .password(passwordEncoder().encode("adminPass")) // to save password in encoded way

                .build();

//        return new InMemoryUserDetailsManager(user1,user2,admin);
        JdbcUserDetailsManager jdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.createUser(user1);
        jdbcUserDetailsManager.createUser(user2);
        jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
