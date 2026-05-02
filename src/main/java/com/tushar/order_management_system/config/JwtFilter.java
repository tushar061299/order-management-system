package com.tushar.order_management_system.config;

import com.tushar.order_management_system.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authReader = request.getHeader("Authorization");

        if (authReader == null || !authReader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = authReader.substring(7);
        String email = jwtUtil.extractEmail(token);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            var user = userRepository.findByEmail(email);
            if(user.isPresent() && jwtUtil.isTokenValid(token, email)){
                var authToken = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        new ArrayList<>()
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
