package com.example.Kukey_Backend.global.handler;

import com.example.Kukey_Backend.global.exception.GlobalException;
import com.example.Kukey_Backend.global.response.BaseErrorResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.*;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(GlobalException.class)
    public BaseErrorResponse handleGeneralException(GlobalException e){
        return new BaseErrorResponse(e.getBaseExceptionResponseStatus());
    }

    //요청한 API가 없을때 예외처리
    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseErrorResponse handleNoHandlerFoundException(NoHandlerFoundException e) {
        return new BaseErrorResponse(NOT_FOUND_API);
    }

    //@Validated 으로 binding error 예외처리
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class, HandlerMethodValidationException.class})
    protected BaseErrorResponse handleMethodArgumentNotValidException(Exception e) {
        return new BaseErrorResponse(INVALID_REQUEST_DTO);
    }

    //HTTP method 예외처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected BaseErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return new BaseErrorResponse(METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    protected BaseErrorResponse handleException(Exception e) {
        return new BaseErrorResponse(INTERNAL_SERVER_ERROR);
    }

}
