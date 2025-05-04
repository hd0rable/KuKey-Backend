package com.example.Kukey_Backend.global.response.status;

import org.springframework.http.HttpStatus;

public interface ResponseStatus {

    int getCode();
    HttpStatus getStatus();
    String getMessage();
}
