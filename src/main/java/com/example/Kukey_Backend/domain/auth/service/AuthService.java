package com.example.Kukey_Backend.domain.auth.service;

import com.example.Kukey_Backend.domain.auth.domain.dto.request.PostAuthAdminLoginRequest;
import com.example.Kukey_Backend.domain.auth.domain.dto.request.PostAuthEmailRequest;
import com.example.Kukey_Backend.domain.auth.domain.dto.request.PostAuthVerifiedCodeRequest;
import com.example.Kukey_Backend.domain.auth.domain.dto.response.PostAuthGetAccessTokenResponse;
import com.example.Kukey_Backend.domain.auth.domain.dto.response.PostAuthSendCodeResponse;
import com.example.Kukey_Backend.global.exception.GlobalException;
import com.example.Kukey_Backend.global.util.JwtUtil;
import com.univcert.api.UnivCert;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    @Value("${univcert.api.key}")
    private String apiKey;
    private final JwtUtil jwtUtil;

    @Value("${kukey.admin.id}")
    private String adminId;
    @Value("${kukey.admin.password}")
    private String adminPassword;

    /**
     * 인증코드 발송
     */
    @SneakyThrows
    public PostAuthSendCodeResponse sendCode(PostAuthEmailRequest postAuthEmailRequest, HttpServletResponse response) {

        if (verifyEmail(postAuthEmailRequest)) {
            String accessToken=issueAndSetUserToken(postAuthEmailRequest.email(), response);
            return new PostAuthSendCodeResponse(true,accessToken);
        }

        Map<String, Object> result = UnivCert.certify(apiKey, postAuthEmailRequest.email(), "건국대학교",true);
        if (!(boolean) result.get("success")) {
            throw new GlobalException(API_ERROR);
        }
        return new PostAuthSendCodeResponse(false,null);
    }

    private boolean verifyEmail(PostAuthEmailRequest postAuthEmailRequest) throws IOException {
        Map<String, Object> result  = UnivCert.status(apiKey, postAuthEmailRequest.email());
        return (boolean) result.get("success");
    }

    /**
     * 인증코드 검증
     */
    @SneakyThrows
    public PostAuthGetAccessTokenResponse verifyCode(PostAuthVerifiedCodeRequest postAuthVerifiedCodeRequest,HttpServletResponse response) {

        Map<String, Object> result = UnivCert.certifyCode(apiKey, postAuthVerifiedCodeRequest.email(), "건국대학교",
                Integer.parseInt(postAuthVerifiedCodeRequest.code()));

        if (!(boolean) result.get("success")) {
            throw new GlobalException(INVALID_AUTH_CODE);
        }

        String accessToken = issueAndSetUserToken(postAuthVerifiedCodeRequest.email(), response);

        return new PostAuthGetAccessTokenResponse(accessToken);

    }

    //인증된 유저에게 토큰 발급
    private String issueAndSetUserToken(String email, HttpServletResponse response) {
        //유저 토큰 발급
        String accessToken = jwtUtil.generateGeneralToken(email,"USER");

        jwtUtil.setHeaderAccessToken(response,accessToken);

        return accessToken;
    }

    /**
     * 인증정보 기억하기
     */
    public PostAuthGetAccessTokenResponse rememberAuthToken(@Valid PostAuthEmailRequest postAuthEmailRequest, HttpServletResponse response) {

        //유저 토큰 발급
        String accessToken = jwtUtil.generateMemoryToken(postAuthEmailRequest.email(),"USER");

        jwtUtil.setHeaderAccessToken(response,accessToken);

        return new PostAuthGetAccessTokenResponse(accessToken);
    }

    /**
     * 관리자 로그인
     */
    public PostAuthGetAccessTokenResponse login(@Valid PostAuthAdminLoginRequest postAuthAdminLoginRequest, HttpServletResponse response) {

        if(postAuthAdminLoginRequest.id().matches(adminId) && postAuthAdminLoginRequest.password().matches(adminPassword)){
            //유저 토큰 발급
            String accessToken = jwtUtil.generateGeneralToken(postAuthAdminLoginRequest.id(),"ADMIN");
            jwtUtil.setHeaderAccessToken(response,accessToken);
            return new PostAuthGetAccessTokenResponse(accessToken);
        }
        throw new GlobalException(LOGIN_FAILED);
    }
}
