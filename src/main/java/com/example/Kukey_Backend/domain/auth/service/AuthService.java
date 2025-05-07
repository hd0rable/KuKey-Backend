package com.example.Kukey_Backend.domain.auth.service;

import com.example.Kukey_Backend.domain.auth.domain.dto.request.PostAuthSendCodeRequest;
import com.example.Kukey_Backend.global.exception.GlobalException;
import com.univcert.api.UnivCert;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.API_ERROR;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    @Value("${univcert.api.key}")
    private String apiKey;

    @SneakyThrows
    public Void sendCode(PostAuthSendCodeRequest postAuthSendCodeRequest) {

        Map<String, Object> result = UnivCert.certify(apiKey, postAuthSendCodeRequest.email(), "건국대학교",true);
        if (!(boolean) result.get("success")) {
            throw new GlobalException(API_ERROR);
        }
        return null;
    }

}
