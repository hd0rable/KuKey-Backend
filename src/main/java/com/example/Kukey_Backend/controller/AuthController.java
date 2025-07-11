package com.example.Kukey_Backend.controller;

import com.example.Kukey_Backend.domain.auth.domain.dto.request.PostAuthAdminLoginRequest;
import com.example.Kukey_Backend.domain.auth.domain.dto.request.PostAuthEmailRequest;
import com.example.Kukey_Backend.domain.auth.domain.dto.request.PostAuthVerifiedCodeRequest;
import com.example.Kukey_Backend.domain.auth.domain.dto.response.PostAuthGetAccessTokenResponse;
import com.example.Kukey_Backend.domain.auth.domain.dto.response.PostAuthSendCodeResponse;
import com.example.Kukey_Backend.domain.auth.service.AuthService;
import com.example.Kukey_Backend.global.response.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    //이메일 인증코드 전송
    @PostMapping("/code")
    public BaseResponse<PostAuthSendCodeResponse> sendCode(@Valid @RequestBody final PostAuthEmailRequest postAuthEmailRequest, HttpServletResponse response) {
        return BaseResponse.ok(authService.sendCode(postAuthEmailRequest, response));
    }

    //이메일 인증코드 검증
    @PostMapping("/verification")
    public BaseResponse<PostAuthGetAccessTokenResponse> verifiedCode(@Valid @RequestBody final PostAuthVerifiedCodeRequest postAuthVerifiedCodeRequest, HttpServletResponse response) {
        return BaseResponse.ok(authService.verifyCode(postAuthVerifiedCodeRequest,response));
    }

    //인증번호 기억하기
    @PostMapping("/memory")
    public BaseResponse<PostAuthGetAccessTokenResponse> rememberAuthToken(@Valid @RequestBody final  PostAuthEmailRequest postAuthEmailRequest, HttpServletResponse response) {
        return BaseResponse.ok(authService.rememberAuthToken(postAuthEmailRequest,response));
    }

    //관리자 로그인
    @PostMapping("/login")
    public BaseResponse<PostAuthGetAccessTokenResponse> adminLogin(@Valid @RequestBody final PostAuthAdminLoginRequest postAuthAdminLoginRequest, HttpServletResponse response) {
        return BaseResponse.ok(authService.login(postAuthAdminLoginRequest,response));
    }

}
