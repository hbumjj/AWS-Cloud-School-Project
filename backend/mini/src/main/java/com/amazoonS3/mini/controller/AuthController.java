package com.amazoonS3.mini.controller;

import com.amazoonS3.mini.model.JwtAuthenticationToken;
import com.amazoonS3.mini.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/api/check-authentication")
    public ResponseEntity<Map<String, String>> storeToken(
            @RequestHeader("Authorization") String token,
            @RequestBody User user) {
        try {
            String jwtToken = token.replace("Bearer ", "");
            String email = user.getEmail();
            String nickname = user.getNickname();

            // Create a custom authentication token with the JWT token and user details
            JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(jwtToken, email, nickname, new ArrayList<>());

            // Set the custom authentication token in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // Prepare the response body with email and nickname
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("email", email);
            responseBody.put("nickname", nickname);

            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private boolean verifyToken(String jwtToken) {
        // Implement token verification logic here
        // You can use AWS SDK or any JWT library to verify the token
        return true; // Replace with actual verification logic
    }
}
