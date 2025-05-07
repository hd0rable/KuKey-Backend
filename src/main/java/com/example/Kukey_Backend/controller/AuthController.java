package com.example.Kukey_Backend.controller;

import com.example.Kukey_Backend.domain.auth.domain.dto.request.PostAuthSendCodeRequest;
import com.example.Kukey_Backend.domain.auth.domain.dto.request.PostAuthVerifiedCodeRequest;
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
    public BaseResponse<PostAuthSendCodeResponse> sendCode(@Valid @RequestBody final PostAuthSendCodeRequest postAuthSendCodeRequest, HttpServletResponse response) {
        return BaseResponse.ok(authService.sendCode(postAuthSendCodeRequest, response));
    }

    //이메일 인증코드 검증
    @PostMapping("/verification")
    public BaseResponse<Void> verifiedCode(@Valid @RequestBody final PostAuthVerifiedCodeRequest postAuthVerifiedCodeRequest, HttpServletResponse response) {
        return BaseResponse.ok(authService.verifyCode(postAuthVerifiedCodeRequest,response));
    }

}
