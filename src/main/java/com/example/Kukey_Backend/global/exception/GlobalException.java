package com.example.Kukey_Backend.global.exception;

import com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{

    private final BaseExceptionResponseStatus baseExceptionResponseStatus;

    public GlobalException(BaseExceptionResponseStatus baseExceptionResponseStatus) {
        super(baseExceptionResponseStatus.getMessage());
        this.baseExceptionResponseStatus = baseExceptionResponseStatus;
    }
}
