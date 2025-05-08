package com.example.Kukey_Backend.global.util;

import com.example.Kukey_Backend.global.exception.GlobalException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.CANNOT_FOUND_TOKEN;
import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.INVALID_TOKEN;
import static com.example.Kukey_Backend.global.util.HttpHeader.AUTHORIZATION;
import static com.example.Kukey_Backend.global.util.HttpHeader.BEARER;


@Component
public class JwtUtil {

    private final Key secretKey;
    private final long accessTokenExpTime;
    private final long memoryAccessTokenExpTime;

    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-expiration}") long accessTokenExpTime,
            @Value("${jwt.memory-access-token-expiration}") long memoryAccessTokenExpTime
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
        this.memoryAccessTokenExpTime = memoryAccessTokenExpTime;
    }

    // JWT 일반토큰 생성
    public String generateGeneralToken(String userEmail, String role) {

        Date now = new Date();
        Date tokenValidity = new Date(now.getTime() + accessTokenExpTime);

        return Jwts.builder()
                .setSubject(userEmail)
                .claim("role", role) // 역할 정보 추가
                .setIssuedAt(now)
                .setExpiration(tokenValidity)
                .signWith(secretKey,SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 메모리토큰 생성
    public String generateMemoryToken(String userEmail, String role) {

        Date now = new Date();
        Date tokenValidity = new Date(now.getTime() + memoryAccessTokenExpTime);

        return Jwts.builder()
                .setSubject(userEmail)
                .claim("role", role) // 역할 정보 추가
                .setIssuedAt(now)
                .setExpiration(tokenValidity)
                .signWith(secretKey,SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 role 추출
    public String extractRole(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new GlobalException(INVALID_TOKEN);
        }
    }


    // 엑세스 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(AUTHORIZATION.getValue(), BEARER.getValue() + accessToken);
    }


    //헤더에서 토큰 추출
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION.getValue());

        if (token == null || !token.startsWith(BEARER.getValue())) {
            throw new GlobalException(CANNOT_FOUND_TOKEN);
        }
        token = token.substring(7); // "Bearer " 제거
        return token;
    }

}
