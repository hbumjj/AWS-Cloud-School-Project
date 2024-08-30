package com.amazoonS3.mini.config;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.JWTClaimsSet;
import java.text.ParseException;

public class TokenUtils {

    public static boolean validateToken(String token) {
        try {
            JWT jwt = JWTParser.parse(token);
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
            // 클레임을 검증하고, 필요한 검증을 수행
            return true; // 검증 로직에 따라 실제 검증 결과 반환
        } catch (ParseException e) {
            return false;
        }
    }
}
