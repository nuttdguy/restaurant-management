package com.restaurant.security;

import com.restaurant.domain.model.User;
import com.restaurant.security.jwt.JwtHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginFilter extends OncePerRequestFilter {

    // pull in the custom configured authentication manager from custom SecurityConfig
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    // apply this filter to all requests that begin with /auth/login
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.trace("LoginFilter - doInternalFilter");

        // extract the userName + password from the header
        String username = request.getHeader("userName");
        String password = request.getHeader("password");

        log.trace("authenticating the user {} with password {}", username, password);
        // authenticate the user with the supplied userName + password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // set the jwt header
        response.setHeader(HttpHeaders.AUTHORIZATION, createJwtToken(authentication));
        response.addHeader("userName", username);
        log.trace("response {}", response.getHeader(HttpHeaders.AUTHORIZATION));
    }

    // create the jwt token with subject and claims
    private String createJwtToken(Authentication authentication) {
        log.trace("LoginFilter - createJwtToken");

        User user = (User) authentication.getPrincipal();
        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return jwtHelper.createToken(user.getUsername(), Map.of("roles", authorities));
    }

    // apply this filter when matches - type Post and uri /auth/login
    // authenticate all other routes through the jwt filter
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        log.trace("LoginFilter - shouldNotFilter");

        String method = request.getMethod();
        String uri = request.getRequestURI();
        boolean isLogin = HttpMethod.POST.matches(method) && uri.startsWith("/v1/api/auth/login");
        return !isLogin;
    }
}
