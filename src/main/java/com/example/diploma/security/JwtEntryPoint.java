package com.example.diploma.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {
    public void commence (HttpServletRequest req, HttpServletResponse res,
                          AuthenticationException exception) throws IOException {
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED,exception.getMessage());
    }
}
