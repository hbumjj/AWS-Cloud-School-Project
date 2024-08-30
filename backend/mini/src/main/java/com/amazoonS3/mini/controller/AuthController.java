package com.amazoonS3.mini.controller;

import com.amazoonS3.mini.service.JwtServiceCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AuthController {

    @Autowired
    private JwtServiceCustom jwtService; // Inject JwtService for token operations

    @GetMapping("/api/check-authentication")
    public ResponseEntity<Void> checkAuthentication(HttpServletRequest request) {
        String token = extractToken(request);

        if (token != null && jwtService.validateToken(token)) {
            return ResponseEntity.ok().build(); // Token is valid
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Token is invalid
    }

    private String extractToken(HttpServletRequest request) {
        // Extract token from cookie or authorization header
        String token = null;

        // Check cookies
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("AUTH_TOKEN".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // Check Authorization header
        if (token == null) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }
        }

        return token;
    }
}
