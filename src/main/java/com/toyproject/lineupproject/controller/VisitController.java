package com.toyproject.lineupproject.controller;


import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.dto.AdminEventRequest;
import com.toyproject.lineupproject.dto.AdminEventResponse;
import com.toyproject.lineupproject.dto.AdminResponse;
import com.toyproject.lineupproject.dto.EventDto;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.service.AdminService;
import com.toyproject.lineupproject.service.EventService;
import com.toyproject.lineupproject.service.VisitService;
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
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Validated
@RequestMapping("/visit")
@Controller
public class VisitController {

    private final AdminService adminService;

    private final EventService eventService;

    private final VisitService visitService;

    private final SearchUtils searchUtils;


    @GetMapping("/searchVisit")
    public String searchReview(
            @RequestParam Map<String, Object> param,
            Principal principal
    ) {
        Admin findUser = adminService.findUserByEmail(principal.getName());
        if (findUser.getRoles().contains("SUPERADMIN")) {
            String uri = searchUtils.getSearchUri(param);
            return "redirect:/visit/all" + (uri == null ? "" : uri);
        } else {
            String uri = searchUtils.getSearchUri(param);
            return "redirect:/visit/admin" + (uri == null ? "" : uri);
        }

    }

    @GetMapping("/{eventId}/new")
    public ModelAndView formVisit(
            @PathVariable("eventId") Long eventId,
            Principal principal
    ) {
        EventDto eventDto = eventService.getEvent(eventId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));

        AdminResponse adminResponse = adminService.verifiedUser(principal.getName());

        return new ModelAndView(
                "visit/newform",
                Map.of("event", eventDto,
                        "admin", adminResponse
                )
        );
    }

    @PostMapping("/{eventId}/new")
    public ModelAndView createVisit(
            @PathVariable("eventId") Long eventId,
            @ModelAttribute @Valid AdminEventRequest request
            ) {

        AdminEventResponse dto = visitService.createVisit(eventId, request);
        return new ModelAndView(
                "alert",
                Map.of(
                        "msg", "정상적으로 신청 되었습니다.",
                        "nextPage", "/visit/" + dto.id()
                )
        );
    }

    @GetMapping("/{adminEventId}")
    public ModelAndView getVisit(
            @PathVariable("adminEventId") Long id,
            @RequestHeader("referer") String referer
    ) {
        AdminEventResponse dto = visitService.findVisit(id);
        String backUrl;
        if (referer.contains("new")) {
            backUrl = "/events";
        } else {
            backUrl = referer;
        }
        return new ModelAndView(
                "visit/detail",
                Map.of("visitInfo",dto,
                        "backUrl",backUrl
                )
        );
    }

    @GetMapping("/{adminEventId}/modify")
    public ModelAndView modifyVisitForm(
            @PathVariable("adminEventId") Long id,
            Principal principal
    ) {
        AdminEventResponse dto = visitService.findVisit(id);
        return new ModelAndView(
                "visit/modifyform",
                Map.of("visitInfo", dto,
                        "backUrl", "/visit/" + id
                )
        );
    }
    @PostMapping("/{adminEventId}/modify")
    public ModelAndView modifiVisit(
            @PathVariable("adminEventId") Long id,
            @ModelAttribute @Valid AdminEventRequest request
    ) {

        AdminEventResponse dto = visitService.modifyVisit(id, request);


        return new ModelAndView(
                "alert",
                Map.of(
                        "msg", "정상적으로 수정 되었습니다.",
                        "nextPage", "/visit/" + dto.id()
                )
        );
    }

    @GetMapping("/{adminEventId}/delete/check")
    public ModelAndView deleteCheck(
            @PathVariable("adminEventId") Long id
    ) {
        return new ModelAndView(
                "confirm",
                Map.of(
                        "msg", "방문 예약 신청을 삭제 하시겠습니까?" ,
                        "nextPage", "/visit/"+id+"/delete",
                        "prevPage", "/visit/"+id
                )
        );
    }

    @GetMapping("/{adminEventId}/delete")
    public ModelAndView deleteVisit(
            @PathVariable("adminEventId") Long id,
            Principal principal
    ) {
        Admin findUser = adminService.findUserByEmail(principal.getName());
        AdminEventResponse findVisit = visitService.findVisit(id);

        if (findUser.getEmail().equals(findVisit.email()) || findUser.getRoles().contains("ADMIN")) {
            visitService.deleteVisit(id);
        } else {
            throw new GeneralException(ErrorCode.ACCESS_DENIED);
        }
        return new ModelAndView(
                "alert",
                Map.of(
                        "msg", "정상적으로 삭제 되었습니다."+'\n'+"메인 페이지로 돌아갑니다.",
                        "nextPage", "/"
                )
        );
    }

    /* 장소 관리자의 요청된 방문 신청 리스트*/

    @GetMapping("/admin")
    public ModelAndView getVisitAdmin(
            Principal principal,
            HttpServletRequest request,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            @RequestParam HashMap<String, Object> param
    ) {
        param.put("placeAdminEmail", principal.getName());
        Page<AdminEventResponse> findDtos = visitService.findVisitByParams(param, pageable);
        Map<String, Object> map = searchUtils.getVisitPageInfo(request, findDtos);
        return new ModelAndView(
                "visit/index",map
        );
    }


    /* Super Admin 의 요청된 전체 방문 신청 리스트*/
    @GetMapping("/all")
    public ModelAndView getVisitSuperAdmin(
            HttpServletRequest request,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            @RequestParam HashMap<String, Object> param
    ) {
        Page<AdminEventResponse> findDtos = visitService.findVisitByParams(param, pageable);
        Map<String, Object> map = searchUtils.getVisitPageInfo(request, findDtos);
        return new ModelAndView(
                "visit/index",map
        );
    }


}
