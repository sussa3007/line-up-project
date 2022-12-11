package com.toyproject.lineupproject.auth.oauth2;

import com.toyproject.lineupproject.auth.jwt.JwtTokenizer;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.Random;


@RequiredArgsConstructor
@Slf4j
public class OAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenizer jwtTokenizer;

    private final AdminService adminService;

    @Value("${SERVICE_URL}")
    private String serviceHost;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        String name = String.valueOf(oAuth2User.getAttributes().get("name"));

        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info(" #### OAuth2 Login Success attributes = {}", attributes.toString());
        // 기존 회원 여부 확인
        Optional<Admin> user = adminService.findUserByEmailToOptional(email);
        if (user.isPresent()) {
            // 토큰 발급
            log.info(" #### OAuth2 Login ");
            jwtTokenizer.delegateToken(user.get(),response);
            redirect(request, response);
        } else {
            // 사용자 가입 후 토큰 발급
            log.info(" #### New OAuth2 Login ");
            Admin newUser = saveUser(email, name);
            jwtTokenizer.delegateToken(newUser,response);
            redirectNewUser(request, response);
        }

    }

    private void redirect(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        log.info(" #### redirect ");
//        String uri = createUri("/").toString();
        String uri = "http://zulseoza.site";
        getRedirectStrategy().sendRedirect(request, response, uri);
    }
    private void redirectNewUser(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        log.info(" #### redirectNewUser ");
//        String uri = createUri("/new-OAuth").toString();
        String uri = "http://zulseoza.site/new-OAuth";
        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    private URI createUri(String uri) {
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host(serviceHost)
                .path(uri)
                .build()
                .toUri();
    }

    private Admin saveUser(String email, String name) {
        log.info(" #### saveUser ");
        Random random = new Random();
        Admin socialLoginUser = Admin.of(
                email,
                name,
                String.valueOf(random.nextInt()),
                null,
                "Social Login User",
                Admin.Status.ACTIVE_USER,
                Admin.LoginBase.SOCIAL_LOGIN
        );
        return adminService.createUser(socialLoginUser);

    }


}
