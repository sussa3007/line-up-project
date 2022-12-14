package com.toyproject.lineupproject.controller.auth;

import com.toyproject.lineupproject.constant.AdminOperationStatus;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.dto.AdminRequest;
import com.toyproject.lineupproject.dto.AdminResponse;
import com.toyproject.lineupproject.dto.ReqResponse;
import com.toyproject.lineupproject.service.AdminService;
import com.toyproject.lineupproject.service.RequestService;
import com.toyproject.lineupproject.utils.SearchUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@Validated
@Controller
public class AdminRegisterController {
    private final AdminService adminService;

    private final RequestService requestService;

    private final SearchUtils searchUtils;

    @GetMapping("/sign-up")
    public ModelAndView signUp(
            @RequestParam(value = "email", required = false ,defaultValue = "") String email,
            @RequestParam(value = "check", required = false ,defaultValue = "false") Boolean check
    ) {

        return new ModelAndView(
                "auth/sign-up",
                Map.of(
                        "email", email,
                        "check", check,
                        "backUrl", "/"
                )
        );
    }

    @PostMapping("/checkEmail")
    public ModelAndView validateEmail(
            @RequestBody String email
    ) {
        boolean val = adminService.verifyUserByEmail(email);
        System.out.println("#### = "+ email);
        if (val) {
            return new ModelAndView(
                    "alert",
                    Map.of(
                            "msg", "가입 가능한 이메일 입니다!.",
                            "nextPage", "/sign-up?"+email+"&check=" + val,
                            "backUrl", "/"
                    )
            );
        } else {
            return new ModelAndView(
                    "alert",
                    Map.of(
                            "msg", "이미 존재하는 이메일 입니다!.",
                            "nextPage", "/sign-up",
                            "backUrl", "/"
                    )
            );
        }
    }

    @PostMapping("/new-signup")
    public ModelAndView register(
            @Valid @ModelAttribute AdminRequest adminRequest
    ) {

        adminService.createUser(adminRequest.dtoToAdmin());

        return new ModelAndView(
                "alert",
                Map.of(
                        "msg", "가입 성공! 다시 로그인 해주세요!.",
                        "nextPage", "/login"
                )
        );
    }

    @GetMapping("/info")
    public ModelAndView userInfo(
            HttpServletRequest request,
            Principal principal,
            @PageableDefault(page = 0, size = 8, sort = "requestId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Admin findUser = adminService.findUserByEmail(principal.getName());
        AdminResponse user = AdminResponse.of(findUser);
        Page<ReqResponse> requests =
                requestService.findAllByUser(pageable, findUser);
        Map<String, Object> requestPageInfo =
                searchUtils.getRequestPageInfo(request, requests, user);

        return new ModelAndView(
                "auth/info",
                requestPageInfo
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
                        "msg", "Logout!! 다시 로그인 해주세요",
                        "adminOperationStatus", AdminOperationStatus.MODIFY
                )
        );
    }

    @GetMapping("/new-OAuth")
    public ModelAndView updateUserBySuperAdmin() {

        return new ModelAndView(
                "alert",
                Map.of(
                        "msg", "가입 성공! 나머지 정보를 입력해 주세요.",
                        "nextPage", "/oauth-info"
                )
        );
    }

    @GetMapping("/oauth-info")
    public ModelAndView oAuthUserInfo(
            Principal principal
    ) {
        Admin findUser = adminService.findUserByEmail(principal.getName());
        AdminResponse user = AdminResponse.of(findUser);
        return new ModelAndView(
                "auth/newoauthinfo",
                Map.of(
                        "adminOperationStatus", AdminOperationStatus.MODIFY,
                        "user", user,
                        "backUrl", "/"
                )
        );
    }
}
