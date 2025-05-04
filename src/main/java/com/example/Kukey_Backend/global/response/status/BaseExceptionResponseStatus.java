package com.example.Kukey_Backend.global.response.status;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


@RequiredArgsConstructor
public enum BaseExceptionResponseStatus implements ResponseStatus {

    SUCCESS(200, OK, "요청에 성공하였습니다."),
    INVALID_REQUEST_DTO(400, BAD_REQUEST, "요청 데이터의 형식이 올바르지 않습니다."),
    NOT_FOUND_API(404, NOT_FOUND, "존재하지 않는 API입니다."),
    METHOD_NOT_ALLOWED(405,HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다."),
    INTERNAL_SERVER_ERROR(500,HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생하였습니다."),

    /**
     * 1000 : room 관련
     */
    CANNOT_FOUND_ROOM(1000, BAD_REQUEST, "해당하는 실습실을 찾을 수 없습니다."),
    INVALID_BUILDING_TYPE(1001, BAD_REQUEST, "알맞은 건물 이름을 찾을 수 없습니다.");

    private final int code;
    private final HttpStatus status;
    private final String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

