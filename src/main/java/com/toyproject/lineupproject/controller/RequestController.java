package com.toyproject.lineupproject.controller;

import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.constant.RequestCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.Request;
import com.toyproject.lineupproject.dto.ReqRequest;
import com.toyproject.lineupproject.dto.ReqResponse;
import com.toyproject.lineupproject.dto.RequestDto;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.service.AdminService;
import com.toyproject.lineupproject.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@Validated
@RequestMapping("/requests")
@Controller
public class RequestController {

    private final AdminService adminService;

    private final RequestService requestService;


    @GetMapping("/new")
    public ModelAndView newRequset(
    ) {
        return new ModelAndView(
                "request/newform",
                Map.of(
                        "requestOption", RequestCode.values()
                )
        );
    }

    @PostMapping("/new")
    public ModelAndView createRequest(
            @Valid @ModelAttribute ReqRequest request
    ) {
        Admin findUser = adminService.findUserByEmail(request.email());
        ReqResponse response =
                requestService.createRequest(request.dtoToRequest(findUser));
        return new ModelAndView(
                "/alert",
                Map.of(
                        "msg", "정상적으로 요청 되었습니다.",
                        "nextPage", "/requests/"+response.id()
                )
        );
    }

    @GetMapping("/{requestId}")
    public ModelAndView getRequest(
            @PathVariable("requestId") Long requestId,
            Principal principal
    ) {
        ReqResponse requestInfo = requestService.findRequest(requestId);
        if (!principal.getName().equals(requestInfo.email())) {
            throw new GeneralException(ErrorCode.ACCESS_DENIED);
        }
        return new ModelAndView(
                "request/detail",
                Map.of(
                        "requestInfo", requestInfo,
                        "backUrl", "/info"
                )
        );
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @GetMapping("/{requestId}/modify")
    public ModelAndView modifyRequest(
            @PathVariable("requestId") Long requestId
    ) {
        ReqResponse requestInfo = requestService.findRequest(requestId);
        return new ModelAndView(
                "/request/modifyform",
                Map.of(
                        "requestInfo", requestInfo,
                        "backUrl", "/requests/all"
                )
        );
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @PostMapping("/{requestId}/modify")
    public ModelAndView modifyRequest(
            @PathVariable("requestId") Long requestId,
            @Valid @ModelAttribute RequestDto request
    ) {
        requestService.updateRequest(requestId, request.dtoToRequest());

        return new ModelAndView(
                "/alert",
                Map.of(
                        "msg", "정상적으로 수정 되었습니다.",
                        "nextPage", "/requests/all"
                )
        );
    }

    @GetMapping("/all")
    public ModelAndView getRequests(
            @PageableDefault Pageable pageable
    ) {
        Page<ReqResponse> requests =
                requestService.findAll(pageable);
        return new ModelAndView(
                "request/index",
                Map.of(
                        "requests", requests,
                        "requestStatus", Request.Status.values()
                )
        );
    }
}
