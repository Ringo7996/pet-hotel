package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Kiểm tra quyền của user đối với moi request
@Component
@Slf4j
public class CustomFilter2 extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Lấy ra email trong session
        String userEmail = (String) request.getSession().getAttribute("MY_SESSION");
        log.info("email = {}", userEmail);
        if (userEmail == null) {
//            response.sendRedirect(request.getContextPath() + "/login");
            filterChain.doFilter(request, response);
            return;
        }
        // Lấy ra thông tin của user
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

        // Tạo đối tượng phân quyền
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}
