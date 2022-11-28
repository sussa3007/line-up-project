package com.toyproject.lineupproject.controller;

import com.toyproject.lineupproject.dto.AdminRequest;
import com.toyproject.lineupproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Validated
@Controller
public class SignUpController {
    private final AdminService adminService;

    @GetMapping("/sign-up")
    public String signUp(){
        return "auth/sign-up";
    }

    @PostMapping("/new-signup")
    public String register(
            @Valid @ModelAttribute AdminRequest adminRequest
    ) {

        adminService.createUser(adminRequest.dtoToAdmin());

        return "redirect:login";
    }
}
