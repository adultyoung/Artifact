package com.artifact.config;


import com.artifact.dao.UserDetailsDao;
import com.artifact.domain.User;
import com.artifact.jwt.JwtConfigurer;
import com.artifact.jwt.JwtTokenProvider;
import com.artifact.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder encoder () {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .csrf()
                .and()
                    .csrf().ignoringAntMatchers("/auth/signin")
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .antMatcher("/**").authorizeRequests()
                        .antMatchers("/", "/login", "error**").permitAll()
                        .antMatchers("/auth/signin").permitAll()
                        .antMatchers(HttpMethod.GET, "/posts/**").permitAll()
                        .antMatchers(HttpMethod.DELETE, "/posts/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/v1/posts/**").permitAll()
                        .antMatchers("/onlyforadmin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/").permitAll()
                .and()
                    .httpBasic().disable()
                    .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserDetailsDao userDetailsDao) {
        return map -> {
            String id = (String) map.get("sub");
            User user = userDetailsDao.findById(id).orElseGet(() -> {
                User addUser = new User();

                addUser.setId(id);
                addUser.setUsername((String)map.get("name"));
                addUser.setEmail((String)map.get("email"));
                addUser.setPicture((String)map.get("picture"));

                return addUser;
            });
            user.setLastVisit(LocalDateTime.now());

            return userDetailsDao.save(user);
        };
    }
}
