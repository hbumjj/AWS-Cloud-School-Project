package com.amazoonS3.mini.controller;

import com.amazoonS3.mini.model.User;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/api/check-authentication")
    public ResponseEntity<String> storeToken(
            @RequestHeader("Authorization") String token,
            @RequestBody User user) {
        try {
            String jwtToken = token.replace("Bearer ", "");

            if (verifyToken(jwtToken)) {
                // Store the token in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(user.getEmail(), null, new ArrayList<>())
                );
                // Optionally add the token to the context if needed
                ((AbstractAuthenticationToken) SecurityContextHolder.getContext()).setDetails(jwtToken);

                // 나중에 꺼내쓸때
                // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                // String jwtToken = (String) SecurityContextHolder.getContext().getDetails();

                return ResponseEntity.ok("Token stored successfully in SecurityContext");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error storing token");
        }
    }


    private boolean verifyToken(String jwtToken) {
        // Implement token verification logic here
        // You can use AWS SDK or any JWT library to verify the token
        return true; // Replace with actual verification logic
    }
}
