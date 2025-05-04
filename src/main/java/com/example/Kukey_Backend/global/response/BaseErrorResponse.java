package com.example.Kukey_Backend.global.response;

import com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus;
import com.example.Kukey_Backend.global.response.status.ResponseStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class BaseErrorResponse implements ResponseStatus {

    private final int code;
    private final HttpStatus status;
    private final String message;
    private final LocalDateTime timestamp;

    public BaseErrorResponse(BaseExceptionResponseStatus baseExceptionResponseStatus) {
        this.code = baseExceptionResponseStatus.getCode();
        this.status = baseExceptionResponseStatus.getStatus();
        this.message = baseExceptionResponseStatus.getMessage();
        this.timestamp = LocalDateTime.now();
    }

}
