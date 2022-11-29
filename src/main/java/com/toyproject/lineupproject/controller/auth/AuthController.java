package com.toyproject.lineupproject.controller.auth;

import com.toyproject.lineupproject.auth.jwt.JwtTokenizer;
import com.toyproject.lineupproject.auth.jwt.utils.CookieUtils;
import com.toyproject.lineupproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@RequiredArgsConstructor
public class AuthController {


    private final AdminService adminService;

    private final JwtTokenizer jwtTokenizer;

    private final CookieUtils cookieUtils;

    @GetMapping("/login")
    public String login() {

        return "auth/login";
    }


    @GetMapping("/logout")
    public String logout(@RequestHeader("referer") String referer, Model model) {
        model.addAttribute("backUrl", referer);

        return "auth/logout";
    }


}
