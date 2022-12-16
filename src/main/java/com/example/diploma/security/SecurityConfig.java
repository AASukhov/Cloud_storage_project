package com.example.diploma.security;

import com.example.diploma.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtEntryPoint entryPoint;

    private CustomUserDetailsService detailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService detailsService, JwtEntryPoint entryPoint) {
        this.detailsService = detailsService;
        this.entryPoint = entryPoint;
    }

    @Bean
    public SecurityFilterChain securityChain (HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager manager (AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtFilter filter(){
        return new JwtFilter();
    }
}
