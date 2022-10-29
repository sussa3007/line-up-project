package com.toyproject.lineupproject.controller;

import com.toyproject.lineupproject.constant.EventStatus;
import com.toyproject.lineupproject.dto.EventResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/events")
@Controller
public class EventController {

    @GetMapping
    public ModelAndView events(){
        HashMap<String, Object> map = new HashMap<>();

        EventResponse eventDetail1 = EventResponse.of(
                1L,
                "오후운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        );

        EventResponse eventDetail2 = EventResponse.of(
                2L,
                "오후운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        );
        map.put("events", List.of(eventDetail1, eventDetail2));
        return new ModelAndView("event/index", map);
    }

    @GetMapping("/{eventId}")
    public ModelAndView eventDetail(@PathVariable Long eventId) {
        HashMap<String, Object> map = new HashMap<>();

        EventResponse eventDetail = EventResponse.of(
                eventId,
                "오후운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        );
        map.put("event", eventDetail);
        return new ModelAndView("event/detail", map);
    }
}
