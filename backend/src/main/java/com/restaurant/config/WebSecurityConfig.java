package com.restaurant.config;

import com.restaurant.exception.api.ApiAccessDeniedHandler;
import com.restaurant.exception.api.ApiEntryPointHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final ApiAccessDeniedHandler apiAccessDeniedHandler;
    private final ApiEntryPointHandler apiEntryPointHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        log.trace("enable cors disable csrf");
        // enable CORS and disable cross site resource sharing
        httpSecurity.cors();
        httpSecurity.csrf().disable();

        // implementing jwt - set session management to stateless
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        log.trace("setting security auth exception handling");
        // Set unauthorized requests exception handler
        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(apiEntryPointHandler)
                .accessDeniedHandler(apiAccessDeniedHandler);

        log.trace("set endpoint permissions");
        // set endpoint permissions, allow all the requests from following endpoints
        httpSecurity.authorizeRequests()
//                .antMatchers(  "/auth/**").permitAll()
                .anyRequest().permitAll();

//        httpSecurity.authenticationProvider(authenticationProvider()).authorizeRequests();
//        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        // build security configuration
        return httpSecurity.build();
    }

}
