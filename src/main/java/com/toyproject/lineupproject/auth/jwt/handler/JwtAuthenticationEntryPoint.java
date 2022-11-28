package com.toyproject.lineupproject.auth.jwt.handler;

import com.toyproject.lineupproject.auth.jwt.utils.CookieUtils;
import com.toyproject.lineupproject.auth.jwt.utils.JwtProperties;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final CookieUtils cookieUtils;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        Object exception = request.getAttribute("exception");
        logExceptionMessage(authException, (Exception) exception);

        if (exception instanceof GeneralException) {
            ErrorCode errorCode = ((GeneralException) exception).getErrorCode();
            if (errorCode.equals(ErrorCode.NOT_FOUND_COOKIE)) {
                response.sendRedirect("/login");
            } else if (errorCode.equals(ErrorCode.EXPIRED_ACCESS_TOKEN)) {
                log.error("### errorURI = {}", request.getRequestURI());
                Cookie cookie = cookieUtils.createCookie(
                        JwtProperties.REDIRECTION_URI,
                        request.getRequestURI(),
                        JwtProperties.EXPIRATION_TIME);
                response.addCookie(cookie);
                response.sendRedirect("/auth/reissue-token");
            }

        } else {
            Exception ex = (Exception) exception;
            log.error("### exception = {}", ex.getMessage());
            response.sendError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        }
    }

    private void logExceptionMessage(AuthenticationException authException, Exception exception) {
        String message = exception != null ? exception.getMessage() : authException.getMessage();
        log.warn("Unauthorized error happened: {}", message);
    }
}
