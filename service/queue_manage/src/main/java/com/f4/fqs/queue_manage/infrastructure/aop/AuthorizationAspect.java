package com.f4.fqs.queue_manage.infrastructure.aop;

import com.f4.fqs.commons.domain.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.f4.fqs.queue_manage.presentation.exception.QueueErrorCode.NOT_AUTHORIZATION;

@Aspect
@Component
public class AuthorizationAspect {

    @Around("@annotation(authorizationRequired) && args(.., httpRequest)")
    public Object checkAuthorization(ProceedingJoinPoint joinPoint, AuthorizationRequired authorizationRequired, HttpServletRequest httpRequest) throws Throwable {
        String rolesHeader = httpRequest.getHeader("X-User-Roles");

        // 권한 검증 로직
        if (!hasRequiredRole(rolesHeader, authorizationRequired.value())) {
            throw new BusinessException(NOT_AUTHORIZATION);
        }

        return joinPoint.proceed();
    }

    private boolean hasRequiredRole(String rolesHeader, String[] requiredRoles) {
        if (rolesHeader == null || rolesHeader.isEmpty()) {
            return false;
        }

        String[] userRoles = rolesHeader.split(","); // 역할이 여러 개일 경우 ,로 구분
        for (String role : requiredRoles) {
            for (String userRole : userRoles) {
                if (userRole.trim().equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }
}
