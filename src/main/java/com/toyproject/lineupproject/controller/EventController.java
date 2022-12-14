package com.toyproject.lineupproject.controller;

import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.constant.EventStatus;
import com.toyproject.lineupproject.dto.EventDto;
import com.toyproject.lineupproject.dto.EventResponse;
import com.toyproject.lineupproject.dto.EventViewResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.service.EventService;
import com.toyproject.lineupproject.utils.SearchUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/events")
@Controller
@Validated
public class EventController {

    private final EventService eventService;

    private final SearchUtils searchUtils;


    @GetMapping("/searchEvent")
    public String searchEvent(
            @RequestParam HashMap<String, Object> param
    ) throws UnsupportedEncodingException {
        String uri = searchUtils.getSearchUri(param);

        return "redirect:/events" + (uri == null ? "" : uri);

    }


    @GetMapping
    public ModelAndView events(
            @RequestParam HashMap<String, Object> param,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<EventDto> findDtos = eventService.getEventsParams(param, pageable);
        Map<String, Object> eventPageInfo = searchUtils.getSearchEventPageInfo(request,findDtos);

        return new ModelAndView("event/index", eventPageInfo);
    }


    @GetMapping("/{eventId}")
    public ModelAndView eventDetail(@PathVariable Long eventId) {
        Map<String, Object> map = new HashMap<>();
        EventResponse event = eventService.getEvent(eventId)
                .map(EventResponse::from)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));

        map.put("event", event);

        return new ModelAndView("event/detail", map);
    }

    @GetMapping("/custom")
    public ModelAndView customEvents(
            @Size(min = 2) String placeName,
            @Size(min = 2) String eventName,
            EventStatus eventStatus,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDatetime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDatetime,
            Pageable pageable
    ) {
        Map<String, Object> map = new HashMap<>();
        Page<EventViewResponse> events = eventService.getEventViewResponse(
                placeName,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                pageable
        );

        map.put("events", events);

        return new ModelAndView("event/index", map);
    }





}
