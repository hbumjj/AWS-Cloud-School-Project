package com.amazoonS3.mini.interceptor;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.GetUserRequest;
import com.amazonaws.services.cognitoidp.model.GetUserResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;
    
    @Value("${aws.cognito.clientId}")
    private String clientId;

    // 리전을 명시적으로 설정하여 클라이언트를 생성합니다.
    private final AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard()
        .withRegion(Regions.AP_NORTHEAST_2)  // 적절한 리전으로 설정합니다.
        .build();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Example of paths that don't require authentication
        String uri = request.getRequestURI();
        if ("/api/check-authentication".equals(uri) || "/api/login".equals(uri) || "/api/register".equals(uri) || "/api/board".equals(uri)) {
            return true;
        }

        // Check for authorization header
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                // Validate the token and get user details
                System.out.println(token);
                GetUserRequest getUserRequest = new GetUserRequest().withAccessToken(token);
                GetUserResult getUserResult = cognitoClient.getUser(getUserRequest);
                System.out.println(getUserResult);
                // Optionally, process user details from the result
                return true;
            } catch (Exception e) {
                // Token validation failed, respond with unauthorized status
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token.");
                response.getWriter().flush();
                return false;
            }
        }
        
        // No authorization header present or invalid, redirect to login
        response.sendRedirect("/login");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
