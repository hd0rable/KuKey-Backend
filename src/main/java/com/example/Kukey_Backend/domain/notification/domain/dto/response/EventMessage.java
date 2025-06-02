package com.example.Kukey_Backend.domain.notification.domain.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EventMessage {
    OPEN_REQUEST_EVENT("에 개방요청이 전송되었습니다! ");
    private final String message;
}