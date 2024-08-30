package com.amazoonS3.mini.config;

import com.amazoonS3.mini.interceptor.AuthInterceptor;
import com.amazoonS3.mini.service.CognitoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${frontend.url}")
    private String FRONTEND_URL;
    
    @Value("${domainName}")
    private String domainName;
    
    @Value("${useSecureCookie}")
    private String useSecureCookie;

    @Autowired
    private AuthInterceptor authInterceptor;


    // private final CognitoService cognitoService;

    // public WebConfig(CognitoService cognitoService) {
    //     this.cognitoService = cognitoService;
    // }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor());
    }
}
