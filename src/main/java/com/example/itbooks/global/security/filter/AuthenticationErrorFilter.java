package com.example.itbooks.global.security.filter;

import com.example.itbooks.auth.application.InvalidTokenException;
import org.springframework.http.HttpStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증 에러처리 필터.
 */
public class AuthenticationErrorFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (InvalidTokenException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
