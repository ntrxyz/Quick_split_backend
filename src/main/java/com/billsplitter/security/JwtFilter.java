package com.billsplitter.security;

import com.billsplitter.model.User;
import com.billsplitter.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil; // ✅ Use JwtUtil instead of hardcoding the secret key

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // ✅ Skip JWT filter for authentication endpoints
        if (requestURI.startsWith("/api/auth/register") || requestURI.startsWith("/api/auth/login")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            String userId = jwtUtil.extractUserId(token);

            if (userId == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token: User ID missing");
                return;
            }

            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return;
            }

            User user = userOptional.get();
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error: " + e.getMessage());
            return;
        }

        chain.doFilter(request, response);
    }

}
