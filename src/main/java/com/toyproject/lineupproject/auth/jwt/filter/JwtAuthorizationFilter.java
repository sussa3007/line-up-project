package com.toyproject.lineupproject.auth.jwt.filter;

import com.toyproject.lineupproject.auth.jwt.JwtTokenizer;
import com.toyproject.lineupproject.auth.jwt.utils.CookieUtils;
import com.toyproject.lineupproject.auth.jwt.utils.JwtAuthorityUtils;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.exception.GeneralException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;

    private final JwtAuthorityUtils authorityUtils;
    private final CookieUtils cookieUtils;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        } catch (SignatureException se) {
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) {
            // todo 만료된 토큰 refresh
            request.setAttribute("exception", new GeneralException(ErrorCode.EXPIRED_ACCESS_TOKEN));
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);


    }


    private Map<String, Object> verifyJws(HttpServletRequest request) {

        Cookie accessTokenCookie = cookieUtils.findAccessTokenCookie(request.getCookies());

        String jws = accessTokenCookie.getValue();

        String base64SecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        return jwtTokenizer.getClaims(jws, base64SecretKey).getBody();
    }


    private void setAuthenticationToContext(Map<String ,Object> claims) {
        String username = (String) claims.get("username");
        List<GrantedAuthority> roles = authorityUtils.createAuthorities((List<String>) claims.get("roles"));
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(username, null, roles);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
