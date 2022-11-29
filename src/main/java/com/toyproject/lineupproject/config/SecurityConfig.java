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
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenizer jwtTokenizer;

    private final JwtAuthorityUtils authorityUtils;

    private final CookieUtils cookieUtils;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/events/**", "/places/**","/new-signup","/sign-up","/auth/reissue-token").permitAll()
                .antMatchers("/logout").hasAnyRole("ADMIN","USER")
                .antMatchers("/info").hasAnyRole("ADMIN","USER")
                .anyRequest().hasRole("ADMIN")
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
                .exceptionHandling().authenticationEntryPoint(
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
                    new JwtAuthenticationFilter(authenticationManager, jwtTokenizer,cookieUtils);

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

}
