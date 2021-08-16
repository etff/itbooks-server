package com.example.itbooks.global.security.filter;

import com.example.itbooks.auth.application.AuthenticationService;
import com.example.itbooks.auth.domain.UserAuthentication;
import com.example.itbooks.user.domain.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * JWT 인증 필터.
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final AuthenticationService authenticationService;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            AuthenticationService authenticationService) {
        super(authenticationManager);
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {
        String authorization = request.getHeader(AUTHORIZATION);

        if (authorization != null) {
            final String accessToken = authorization.substring(BEARER.length());
            final Long userId = authenticationService.parseToken(accessToken);
            final List<Role> roles = authenticationService.roles(userId);
            final Authentication authentication =
                    new UserAuthentication(userId, roles);

            final SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
