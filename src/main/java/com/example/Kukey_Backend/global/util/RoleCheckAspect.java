package com.example.Kukey_Backend.global.util;

import com.example.Kukey_Backend.global.annotation.RoleRequired;
import com.example.Kukey_Backend.global.exception.GlobalException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;

import static com.example.Kukey_Backend.global.response.status.BaseExceptionResponseStatus.ACCESS_DENIED;

@Aspect
@Component
public class RoleCheckAspect {

    private final JwtUtil jwtUtil;

    public RoleCheckAspect(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Around("@annotation(com.example.Kukey_Backend.global.annotation.RoleRequired) || " +
            "@within(com.example.Kukey_Backend.global.annotation.RoleRequired)")
    public Object checkRole(ProceedingJoinPoint joinPoint) throws Throwable {

            // 1. 어노테이션 추출 (메서드 → 클래스 순)
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            RoleRequired roleRequired = method.getAnnotation(RoleRequired.class);

            if (roleRequired == null) { // 메서드에 없으면 클래스에서 추출
                roleRequired = method.getDeclaringClass().getAnnotation(RoleRequired.class);
            }

            // 2. 어노테이션이 없으면 통과
            if (roleRequired == null) {
                return joinPoint.proceed();
            }

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
