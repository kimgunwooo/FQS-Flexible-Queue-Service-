package com.f4.fqs.queue.infrastructure.aop;

import com.f4.fqs.commons.domain.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Aspect
class AuthorizationAspectTest {

    private AuthorizationAspect authorizationAspect;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private ServerWebExchange exchange;

    @Mock
    private ServerHttpRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authorizationAspect = new AuthorizationAspect();
    }

    @Test
    void testCheckAuthorizationWithValidRole() throws Throwable {
        // Mocking the behavior of ServerHttpRequest
        when(exchange.getRequest()).thenReturn(request);
        when(request.getHeaders()).thenReturn(HttpHeaders.readOnlyHttpHeaders(
                new HttpHeaders() {{
                    add("X-User-Roles", "ROLE_USER");
                }}
        ));

        // Mocking the AuthorizationRequired annotation
        AuthorizationRequired authorizationRequired = mock(AuthorizationRequired.class);
        when(authorizationRequired.value()).thenReturn(new String[]{"ROLE_USER"});

        // Setting up the mock for joinPoint.proceed()
        when(joinPoint.proceed()).thenReturn("Success");

        // Call the method to test
        Object result = authorizationAspect.checkAuthorization(joinPoint, authorizationRequired, exchange);

        // Verify that the proceed method was called
        verify(joinPoint).proceed();

        // Validate the result
        assert result.equals("Success");
    }

    @Test
    void testCheckAuthorizationWithInvalidRole() {
        // Mocking the behavior of ServerHttpRequest
        when(exchange.getRequest()).thenReturn(request);
        when(request.getHeaders()).thenReturn(HttpHeaders.readOnlyHttpHeaders(
                new HttpHeaders() {{
                    add("X-User-Roles", "ROLE_ADMIN");
                }}
        ));

        // Mocking the AuthorizationRequired annotation
        AuthorizationRequired authorizationRequired = mock(AuthorizationRequired.class);
        when(authorizationRequired.value()).thenReturn(new String[]{"ROLE_USER"});

        // Call the method to test and expect an exception
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            authorizationAspect.checkAuthorization(joinPoint, authorizationRequired, exchange);
        });

        // Validate the exception message
        assertEquals(403, exception.getErrorCode().getStatus());
    }
}