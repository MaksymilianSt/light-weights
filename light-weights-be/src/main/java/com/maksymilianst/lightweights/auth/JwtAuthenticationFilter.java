package com.maksymilianst.lightweights.auth;

import com.maksymilianst.lightweights.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String JWT_COOKIE = "jwt";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain
    ) throws ServletException, IOException {

        final String token, userName;

        Cookie jwtCookie = WebUtils.getCookie(req, JWT_COOKIE);
        token = jwtCookie != null ? jwtCookie.getValue() : null;

        if (token == null) {
            filterChain.doFilter(req, resp);
            return;
        }
        userName = jwtService.extractUsername(token);

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user = this.userDetailsService.loadUserByUsername(userName);

            if (jwtService.isTokenValid(token, user)) {
                authenticate(user, req);
            }
        }
        filterChain.doFilter(req, resp);
    }

    private void authenticate(UserDetails user, HttpServletRequest req) {
        var authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
