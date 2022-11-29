package com.toyproject.lineupproject.controller.auth;

import com.toyproject.lineupproject.constant.AdminOperationStatus;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.dto.AdminRequest;
import com.toyproject.lineupproject.dto.AdminResponse;
import com.toyproject.lineupproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@Validated
@Controller
public class AdminRegisterController {
    private final AdminService adminService;

    @GetMapping("/sign-up")
    public String signUp() {
        return "auth/sign-up";
    }

    @PostMapping("/new-signup")
    public String register(
            @Valid @ModelAttribute AdminRequest adminRequest
    ) {

        adminService.createUser(adminRequest.dtoToAdmin());

        return "redirect:login";
    }

    @GetMapping("/info")
    public ModelAndView userInfo(
            Principal principal

    ) {
        Admin findUser = adminService.findUserByEmail(principal.getName());
        AdminResponse user = AdminResponse.of(findUser);
        return new ModelAndView(
                "auth/info",
                Map.of(
                        "adminOperationStatus", AdminOperationStatus.MODIFY,
                        "user", user
                )
        );
    }

    @PostMapping("/info")
    public ModelAndView updateUser(
            @Valid @ModelAttribute AdminRequest adminRequest
    ) {
        adminService.updateUser(adminRequest.dtoToAdmin());
        return new ModelAndView(
                "auth/infoconfirm",
                Map.of(
                        "msg", "자동으로 로그아웃 됩니다.",
                        "adminOperationStatus", AdminOperationStatus.MODIFY
                )
        );
    }
}
