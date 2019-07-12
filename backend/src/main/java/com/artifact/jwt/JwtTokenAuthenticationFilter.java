package com.artifact.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String COOKIE_BEARER = "COOKIE-BEARER";

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenAuthenticationFilter(String url, JwtTokenProvider jwtTokenProvider, AuthenticationManager authentication) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authentication);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        Cookie cookie = WebUtils.getCookie(request, COOKIE_BEARER);
        String token = cookie != null ? cookie.getValue() : null;

        if (token != null && jwtTokenProvider.validateToken(token)) {
            return jwtTokenProvider.getAuthentication(token);
        }else {
            AuthenticationRequest data = new ObjectMapper().readValue(
                    request.getInputStream(), AuthenticationRequest.class);
                Authentication auth =  getAuthenticationManager()
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                data.getUsername(), data.getPassword()));
            jwtTokenProvider.addAuth(response, auth);
            return auth;
        }
    }

    @Data
    static class AuthenticationRequest {
        private String username;
        private String password;
    }
}
