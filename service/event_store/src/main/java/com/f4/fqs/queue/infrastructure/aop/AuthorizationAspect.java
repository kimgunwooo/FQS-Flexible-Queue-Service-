package com.f4.fqs.queue.infrastructure.aop;

import com.f4.fqs.commons.domain.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static com.f4.fqs.queue.presentation.exception.QueueErrorCode.NOT_AUTHORIZATION;

@Aspect
@Component
public class AuthorizationAspect {

    @Around("@annotation(authorizationRequired) && args(.., exchange)")
    public Object checkAuthorization(ProceedingJoinPoint joinPoint, AuthorizationRequired authorizationRequired, ServerWebExchange exchange) throws Throwable {
        String rolesHeader = exchange.getRequest().getHeaders().getFirst("X-User-Roles");

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

        String[] userRoles = rolesHeader.split(","); // TODO. 역할이 여러개라면 , 로 구분?
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
