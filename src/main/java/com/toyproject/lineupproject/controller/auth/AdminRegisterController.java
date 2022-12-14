package com.toyproject.lineupproject.controller.auth;

import com.toyproject.lineupproject.constant.AdminOperationStatus;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.dto.AdminEventResponse;
import com.toyproject.lineupproject.dto.AdminRequest;
import com.toyproject.lineupproject.dto.AdminResponse;
import com.toyproject.lineupproject.dto.ReqResponse;
import com.toyproject.lineupproject.service.AdminService;
import com.toyproject.lineupproject.service.RequestService;
import com.toyproject.lineupproject.service.VisitService;
import com.toyproject.lineupproject.utils.SearchUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    private final VisitService visitService;

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
            @RequestParam String email
    ) {
        boolean val = adminService.verifyUserByEmail(email);
        if (val) {
            return new ModelAndView(
                    "alert",
                    Map.of(
                            "msg", "?????? ????????? ????????? ?????????!.",
                            "nextPage", "/sign-up?email="+email+"&check=" + val,
                            "backUrl", "/"
                    )
            );
        } else {
            return new ModelAndView(
                    "alert",
                    Map.of(
                            "msg", "?????? ???????????? ????????? ?????????!.",
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
                        "msg", "?????? ??????! ?????? ????????? ????????????!.",
                        "nextPage", "/login"
                )
        );
    }

    @GetMapping("/info")
    public ModelAndView userInfo(
            HttpServletRequest request,
            Principal principal,
            @PageableDefault(page = 0, size = 8, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Admin findUser = adminService.findUserByEmail(principal.getName());
        AdminResponse user = AdminResponse.of(findUser);
        Page<ReqResponse> requests =
                requestService.findAllByUser(pageable, findUser);
        Map<String, Object> requestPageInfo =
                searchUtils.getRequestPageInfo(request, requests, user);
        Page<AdminEventResponse> visitByAdmin = visitService.findVisitByAdmin(findUser, pageable);
        requestPageInfo.put("visits", visitByAdmin);
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
                        "msg", "Logout!! ?????? ????????? ????????????",
                        "adminOperationStatus", AdminOperationStatus.MODIFY
                )
        );
    }

    @GetMapping("/new-OAuth")
    public ModelAndView updateUserBySuperAdmin() {

        return new ModelAndView(
                "alert",
                Map.of(
                        "msg", "?????? ??????! ????????? ????????? ????????? ?????????.",
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
