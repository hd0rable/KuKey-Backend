package com.example.Kukey_Backend.domain.auth.service;

import com.example.Kukey_Backend.domain.auth.domain.dto.request.PostAuthSendCodeRequest;
import com.example.Kukey_Backend.domain.auth.domain.dto.request.PostAuthVerifiedCodeRequest;
import com.example.Kukey_Backend.domain.auth.domain.dto.response.PostAuthSendCodeResponse;
import com.example.Kukey_Backend.global.exception.GlobalException;
import com.example.Kukey_Backend.global.util.JwtUtil;
import com.univcert.api.UnivCert;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.API_ERROR;
import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.INVALID_AUTH_CODE;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    @Value("${univcert.api.key}")
    private String apiKey;
    private final JwtUtil jwtUtil;

    /**
     * 인증코드 발송
     */
    @SneakyThrows
    public PostAuthSendCodeResponse sendCode(PostAuthSendCodeRequest postAuthSendCodeRequest, HttpServletResponse response) {

        if (verifyEmail(postAuthSendCodeRequest)) {
            issueAndSetUserToken(postAuthSendCodeRequest.email(), response);
            return new PostAuthSendCodeResponse(true);
        }

        Map<String, Object> result = UnivCert.certify(apiKey, postAuthSendCodeRequest.email(), "건국대학교",true);
        if (!(boolean) result.get("success")) {
            throw new GlobalException(API_ERROR);
        }
        return new PostAuthSendCodeResponse(false);
    }

    private boolean verifyEmail(PostAuthSendCodeRequest postAuthSendCodeRequest) throws IOException {
        Map<String, Object> result  = UnivCert.status(apiKey, postAuthSendCodeRequest.email());
        return (boolean) result.get("success");
    }

    /**
     * 인증코드 검증
     */
    @SneakyThrows
    public Void verifyCode(PostAuthVerifiedCodeRequest postAuthVerifiedCodeRequest,HttpServletResponse response) {

        Map<String, Object> result = UnivCert.certifyCode(apiKey, postAuthVerifiedCodeRequest.email(), "건국대학교",
                Integer.parseInt(postAuthVerifiedCodeRequest.code()));

        if (!(boolean) result.get("success")) {
            throw new GlobalException(INVALID_AUTH_CODE);
        }

        issueAndSetUserToken(postAuthVerifiedCodeRequest.email(), response);

        return null;

    }

    //인증된 유저에게 토큰 발급
    private void issueAndSetUserToken(String email, HttpServletResponse response) {
        //유저 토큰 발급
        String accessToken = jwtUtil.generateToken(email,"USER");

        jwtUtil.setHeaderAccessToken(response,accessToken);
    }

}
