package com.restaurant.security;

import com.restaurant.exception.api.ApiAccessDeniedHandler;
import com.restaurant.exception.api.ApiEntryPointHandler;
import com.restaurant.repository.IUserRepo;
import com.restaurant.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

import static com.restaurant.exception.ExceptionMessage.NOT_FOUND;
import static java.lang.String.format;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true, // enable @Secured annotation
        jsr250Enabled = true,  // allows use of @RoleAllowed
        prePostEnabled = true // enable pre/post annotations
)
@AllArgsConstructor
public class SecurityConfig {

    private IUserRepo userRepo;
    private ApiAccessDeniedHandler apiAccessDeniedHandler;
    private ApiEntryPointHandler apiEntryPointHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // create a Bcrypt password encoder bean
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {

        // build an authentication manager utilizing the userRepo / dao impl and bcrypt password encoder
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(username -> userRepo.findByUsername(username)
                                        .orElseThrow(() -> new UsernameNotFoundException(format(NOT_FOUND, username))))
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, LoginFilter loginFilter, JwtFilter jwtFilter) throws Exception {
        log.trace("SecurityConfig - configuring security filter chain");
        // enable CORS and disable cross site resource sharing
        httpSecurity.cors();
        httpSecurity.csrf().disable();

        // require authorization for any request
        httpSecurity.authorizeRequests()
                .antMatchers("/auth/register", "/auth/verify/**" ).permitAll()
                .anyRequest().authenticated();

        // set custom filters, inject the dependencies that have been created by Spring @Component annotation
        httpSecurity.addFilterAt(loginFilter, BasicAuthenticationFilter.class);
        httpSecurity.addFilterAt(jwtFilter, BasicAuthenticationFilter.class);

        // implementing jwt - set session management to stateless
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Set exception handlers
        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(apiEntryPointHandler)
                .accessDeniedHandler(apiAccessDeniedHandler);

        // build security configuration
        return httpSecurity.build();
    }

}
