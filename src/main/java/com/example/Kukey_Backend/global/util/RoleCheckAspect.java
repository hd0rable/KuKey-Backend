package com.example.Kukey_Backend.global.util;

import com.example.Kukey_Backend.global.annotation.RoleRequired;
import com.example.Kukey_Backend.global.exception.GlobalException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.ACCESS_DENIED;

@Aspect
@Component
public class RoleCheckAspect {

    private final JwtUtil jwtUtil;

    public RoleCheckAspect(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Around("@annotation(roleRequired)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RoleRequired roleRequired) throws Throwable {

        // HTTP 요청에서 토큰 추출
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = jwtUtil.getToken(request);

        // 토큰 유효성 검사
        jwtUtil.validateToken(token);

        // 역할 확인
        String requiredRole = roleRequired.value();
        String userRole = jwtUtil.extractRole(token);

        if (!requiredRole.equals(userRole)) {
            throw new GlobalException(ACCESS_DENIED);
        }

        return joinPoint.proceed(); // 원래 메서드 실행
    }
}
