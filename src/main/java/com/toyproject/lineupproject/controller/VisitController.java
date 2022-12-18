package com.toyproject.lineupproject.controller;


import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.dto.AdminEventRequest;
import com.toyproject.lineupproject.dto.AdminEventResponse;
import com.toyproject.lineupproject.dto.AdminResponse;
import com.toyproject.lineupproject.dto.EventDto;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.service.AdminService;
import com.toyproject.lineupproject.service.EventService;
import com.toyproject.lineupproject.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@Validated
@RequestMapping("/visit")
@Controller
public class VisitController {

    private final AdminService adminService;

    private final EventService eventService;

    private final VisitService visitService;

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
            @PathVariable("adminEventId") Long id
    ) {
        AdminEventResponse dto = visitService.findVisit(id);
        return new ModelAndView(
                "visit/detail",
                Map.of("visitInfo",dto,
                        "backUrl","/events"
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
                "visit/modifyForm",
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


        return new ModelAndView(
                "alert",
                Map.of(
                        "msg", "정상적으로 수정 되었습니다.",
                        "nextPage", "/visit/" + dto.id()
                )
        );
    }
}
