package com.amazoonS3.mini.controller;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazoonS3.mini.model.User;
import com.amazoonS3.mini.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Value("${domainName}")
    private String domainName;

    @Autowired
    private UserService userService;

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        // 브라우저 캐시 방지 헤더 설정
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }

        // 쿠키 무효화
        Cookie idCookie = new Cookie("JSESSIONID", null); // 쿠키 값을 null로 설정
        idCookie.setPath("/");
        idCookie.setDomain(domainName); // 동일한 도메인 설정
        idCookie.setMaxAge(0); // Max-Age를 0으로 설정하여 쿠키 즉시 삭제
        idCookie.setHttpOnly(true);
        idCookie.setSecure(true);
        response.addCookie(idCookie); // 쿠키 추가

        return ResponseEntity.ok().body("Logged out successfully");
    }


    @PostMapping("/reset-password")
    public void resetPassword(
        @RequestParam String username,
        @RequestParam String newPassword) {
        userService.resetPassword(username, newPassword);
    }
}
