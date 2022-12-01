package com.toyproject.lineupproject.config;

import com.toyproject.lineupproject.auth.jwt.JwtTokenizer;
import com.toyproject.lineupproject.auth.jwt.filter.JwtAuthenticationFilter;
import com.toyproject.lineupproject.auth.jwt.filter.JwtAuthorizationFilter;
import com.toyproject.lineupproject.auth.jwt.handler.CustomAuthenticationFailureHandler;
import com.toyproject.lineupproject.auth.jwt.handler.CustomAuthenticationSuccessHandler;
import com.toyproject.lineupproject.auth.jwt.handler.JwtAccessDeniedHandler;
import com.toyproject.lineupproject.auth.jwt.handler.JwtAuthenticationEntryPoint;
import com.toyproject.lineupproject.auth.jwt.utils.CookieUtils;
import com.toyproject.lineupproject.auth.jwt.utils.JwtAuthorityUtils;
import com.toyproject.lineupproject.auth.jwt.utils.JwtProperties;
import com.toyproject.lineupproject.auth.oauth2.OAuth2UserSuccessHandler;
import com.toyproject.lineupproject.auth.oauth2.provider.CustomOAuth2Provider;
import com.toyproject.lineupproject.auth.oauth2.service.CustomOAuth2UserService;
import com.toyproject.lineupproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${GOOGLE_AOUTH2_SAMPLEPROJECT_SECRETKEY}")
    private String clientSecret;

    @Value("${KAKAO_AOUTH2_SECRETKEY}")
    private String kakaoClientSecret;

    private final JwtTokenizer jwtTokenizer;

    private final JwtAuthorityUtils authorityUtils;

    private final CookieUtils cookieUtils;

    private final AdminService adminService;

    private final CustomOAuth2UserService oAuth2UserService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                // Super Admin
                .mvcMatchers(
                        "/admin/user-all",
                        "/admin/modify",
                        "/admin/user/**",
                        "/admin/events-all",
                        "/admin/places-all",
                        "/admin/user-all",
                        "/requests/all"

                )
                .hasRole("SUPERADMIN")
                .antMatchers("/logout")
                .hasAnyRole("SUPERADMIN", "ADMIN", "USER")
                // Register
                .antMatchers( "/info","/oauth-info")
                .hasAnyRole("SUPERADMIN", "ADMIN", "USER")

                // Requests
                .antMatchers("/requests/**")
                .hasAnyRole("SUPERADMIN", "ADMIN", "USER")
                // Admin
                .antMatchers("/admin/**").hasAnyRole("SUPERADMIN", "ADMIN")
                .anyRequest().permitAll()
                .and()
                .apply(new CustomFilterConfig())
                .and()
                .cors(Customizer.withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable()
                .formLogin()
                .permitAll()
                .loginPage("/login")
                .defaultSuccessUrl("/events")
                .permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        new JwtAuthenticationEntryPoint(cookieUtils)
                )
                .accessDeniedHandler(new JwtAccessDeniedHandler(cookieUtils))
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/events")
                .deleteCookies(JwtProperties.COOKIE_NAME_ACCESS_TOKEN)
                .deleteCookies(JwtProperties.COOKIE_NAME_REFRESH_TOKEN)
                .deleteCookies(JwtProperties.REDIRECTION_URI)
                .and()
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2UserSuccessHandler(jwtTokenizer, adminService))
                        .userInfoEndpoint().userService(oAuth2UserService)

                )

        ;
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    public class CustomFilterConfig extends AbstractHttpConfigurer<CustomFilterConfig, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager =
                    builder.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter =
                    new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);

            jwtAuthenticationFilter.setAuthenticationSuccessHandler(
                    new CustomAuthenticationSuccessHandler(cookieUtils));
            jwtAuthenticationFilter.setAuthenticationFailureHandler(
                    new CustomAuthenticationFailureHandler()
            );

            jwtAuthenticationFilter.setRequiresAuthenticationRequestMatcher(
                    new AntPathRequestMatcher("/login","POST"));

            JwtAuthorizationFilter jwtAuthorizationFilter =
                    new JwtAuthorizationFilter(jwtTokenizer, authorityUtils,cookieUtils);

            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtAuthorizationFilter, JwtAuthenticationFilter.class);

        }
    }

    private ClientRegistration clientRegistration(
            OAuth2ClientProperties clientProperties,
            String client
    ) {
        if("google".equals(client)) {
            OAuth2ClientProperties.Registration google = clientProperties.getRegistration().get("google");
            return CustomOAuth2Provider.GOOGLE
                    .getBuilder(client)
                    .clientId(google.getClientId())
                    .clientSecret(clientSecret)
                    .build();
        }
        if("kakao".equals(client)) {
            OAuth2ClientProperties.Registration kakao = clientProperties.getRegistration().get("kakao");
            return CustomOAuth2Provider.KAKAO
                    .getBuilder(client)
                    .clientId(kakao.getClientId())
                    .clientSecret(kakaoClientSecret)
//                    .jwkSetUri("temp")
                    .build();
        }
        return null;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            OAuth2ClientProperties oAuth2ClientProperties
    ) {
        List<ClientRegistration> registrations = oAuth2ClientProperties
                .getRegistration().keySet().stream()
                .map(client -> clientRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new InMemoryClientRegistrationRepository(registrations);
    }


}
