package com.toyproject.lineupproject.auth.jwt.filter;

import com.toyproject.lineupproject.auth.jwt.JwtTokenizer;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Admin;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenizer jwtTokenizer;


    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.getParameter("username"),request.getParameter("password"));

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        log.info("@ Login Successful");
        Admin admin = (Admin) authResult.getPrincipal();
        jwtTokenizer.delegateToken(admin,response);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);

    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {
        String referer = request.getHeader("REFERER").replace("http://localhost:8080", "");
        request.setAttribute("msg", ErrorCode.FAILED_LOGIN.getMessage()+" Return to previous page");
        request.setAttribute("nextPage", referer);
        request.getRequestDispatcher("/error").forward(request, response);
    }
}
