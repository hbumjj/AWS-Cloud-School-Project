package com.amazoonS3.mini.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminGetUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CognitoService {

    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    @Value("${aws.cognito.clientId}")
    private String clientId;

    private final AWSCognitoIdentityProvider cognitoClient;

    public CognitoService() {
        // 리전을 명시적으로 설정하여 클라이언트를 생성합니다.
        this.cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard()
            .withRegion(Regions.AP_NORTHEAST_2)  // 적절한 리전으로 설정합니다.
            .build();
    }

    public AdminGetUserResult getUser(String username) {
        AdminGetUserRequest request = new AdminGetUserRequest()
            .withUserPoolId(userPoolId)
            .withUsername(username);

        return cognitoClient.adminGetUser(request);
    }
}
