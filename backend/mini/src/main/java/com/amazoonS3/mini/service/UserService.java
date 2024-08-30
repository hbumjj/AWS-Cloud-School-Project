package com.amazoonS3.mini.service;

import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;
import com.amazoonS3.mini.mapper.UserMapper;
import com.amazoonS3.mini.model.JwtAuthenticationToken;
import com.amazoonS3.mini.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void getUserInfoByCognito() {
        // Retrieve the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Check if the authentication object is an instance of JwtAuthenticationToken
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;
            
            // Access the JWT token, email, and nickname
            String token = jwtAuthToken.getToken();
            String email = jwtAuthToken.getEmail();
            String nickname = jwtAuthToken.getNickname();
            
            // Use these details as needed
            System.out.println("Token: " + token);
            System.out.println("Email: " + email);
            System.out.println("Nickname: " + nickname);
        } else {
            // Handle cases where the authentication token is not of the expected type
            System.out.println("No JwtAuthenticationToken found in SecurityContext.");
        }
    }
    
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
    }

    public User authenticateUser(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public void resetPassword(String username, String newPassword) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updatePassword(user);
    }

    public String getUsernameFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
