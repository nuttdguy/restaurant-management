package com.restaurant.security;

import com.restaurant.security.jwt.JwtHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.trace("JwtFilter - doInternalFilter");
        // extract the token from the request header
        String token = getToken(request);
        log.trace("doFilterInternal - jwt filter security chain incoming request {}", token);
        try {
            // extract the claims from the token
            Map<String, Object> claims = jwtHelper.parseClaims(token);
            // set context to this user and their roles / authorities
            Authentication authenticated = createAuthentication(claims);
            log.trace("doFilterInternal - jwt authenticated {}", authenticated);

            SecurityContextHolder.getContext().setAuthentication(authenticated);
        } catch (ExpiredJwtException ex ) {
            log.trace(ex.getLocalizedMessage());
        }

        log.trace("JwtFilter - continue doFilter");
        // continue spring security filter chain
        filterChain.doFilter(request, response);
    }

    private Authentication createAuthentication(Map<String, Object> claims) {
        log.trace("JwtFilter - createAuthentication");
        // extract the claims, i.e. user authorities
        Set<String> roles = Set.of(claims.get("roles").toString().split(","));
        // create new simple granted authority instances for each role / authorization claim
        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> "ROLE_"+role)  // append ROLE_ b/c spring prefixes roles
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        // return an authentication object with claims and authorities
        return new UsernamePasswordAuthenticationToken(claims.get(Claims.SUBJECT), null, authorities);
    }

    private String getToken(HttpServletRequest request) {
        log.trace("JwtFilter - getToken");
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(auth -> auth.startsWith("Bearer "))
                .map(auth -> auth.replace("Bearer ", ""))
                .orElseThrow(() -> new BadCredentialsException("Invalid token"));
    }

    // do not apply this filter to /auth/login route
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.trace("jwt - shouldNotFilter uri {}", uri);

        // do not use the jwt filter for register or verify routes
        return uri.startsWith("/v1/api/auth/register") || uri.startsWith("/v1/api/auth/verify") ||
                uri.startsWith("/v1/api/auth/password") || uri.startsWith("/v1/api/auth/login");
    }
}
