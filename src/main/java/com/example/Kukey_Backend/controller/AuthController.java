package com.example.Kukey_Backend.controller;

import com.example.Kukey_Backend.domain.auth.domain.dto.request.PostAuthSendCodeRequest;
import com.example.Kukey_Backend.domain.auth.service.AuthService;
import com.example.Kukey_Backend.global.response.BaseResponse;
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

    @PostMapping("/code")
    public BaseResponse<Void> sendCode(@Valid @RequestBody final PostAuthSendCodeRequest postAuthSendCodeRequest) {
        return BaseResponse.ok(authService.sendCode(postAuthSendCodeRequest));
    }

}
