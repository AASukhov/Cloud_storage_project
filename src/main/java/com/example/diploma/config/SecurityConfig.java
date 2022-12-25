package com.example.diploma.config;

import com.example.diploma.security.JwtEntryPoint;
import com.example.diploma.security.JwtFilter;
import com.example.diploma.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
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
        http.addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class);
        http./*cors().and().*/csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/login","/register")
                .permitAll()
                .anyRequest()
                .authenticated()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/file**","/user")
//                .permitAll()
//
                .and()
//                .logout()
//                .invalidateHttpSession(true)
//                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
//                .clearAuthentication(true)
//                .logoutSuccessUrl("/login")
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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
