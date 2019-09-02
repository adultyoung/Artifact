package com.artifact.config;


import com.artifact.dao.UserDetailsDao;
import com.artifact.domain.User;
import com.artifact.jwt.JwtTokenAuthenticationFilter;
import com.artifact.jwt.JwtTokenProvider;
import com.artifact.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:8080", "*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .ignoringAntMatchers("/login")
                .ignoringAntMatchers("/auth/oauth", "/post/**", "/comment/**", "/profile/**")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/login", "/registration/**", "/sockjs-node/**", "/gs-guide-websocket/**", "/assets/**", "/auth/oauth", "error**").permitAll()
                .antMatchers("/auth/signin").permitAll()
                .antMatchers(HttpMethod.GET, "/posts/**").hasAuthority("USER")
                .antMatchers(HttpMethod.DELETE, "/posts/**").hasAuthority("USER")
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .addFilterBefore(new JwtTokenAuthenticationFilter("/login", jwtTokenProvider, authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserDetailsDao userDetailsDao) {
        return map -> {
            String id = (String) map.get("sub");
            User user = userDetailsDao.findById(id).orElseGet(() -> {
                User addUser = new User();

                addUser.setId(id);
                addUser.setUsername((String) map.get("name"));
                addUser.setEmail((String) map.get("email"));
                addUser.setPicture((String) map.get("picture"));

                return addUser;
            });
            user.setLastVisit(LocalDateTime.now());

            return userDetailsDao.save(user);
        };
    }
}
