package com.toyproject.lineupproject.controller;

import com.querydsl.core.types.Predicate;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.constant.EventStatus;
import com.toyproject.lineupproject.constant.PlaceType;
import com.toyproject.lineupproject.domain.Event;
import com.toyproject.lineupproject.domain.Place;
import com.toyproject.lineupproject.dto.EventResponse;
import com.toyproject.lineupproject.dto.PlaceResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.service.EventService;
import com.toyproject.lineupproject.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final EventService eventService;
    private final PlaceService placeService;

    @GetMapping("/places")
    public ModelAndView adminPlaces(@QuerydslPredicate(root = Place.class) Predicate predicate) {
        List<PlaceResponse> places = placeService.getPlaces(predicate)
                .stream()
                .map(PlaceResponse::from)
                .toList();

        return new ModelAndView(
                "admin/places",
                Map.of(
                        "places", places,
                        "placeType", PlaceType.values()
                )
        );
    }

    @GetMapping("/places/{placeId}")
    public ModelAndView adminPlaceDetail(@PathVariable Long placeId) {
        PlaceResponse place = placeService.getPlace(placeId)
                .map(PlaceResponse::from)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));

        return new ModelAndView(
                "admin/place-detail",
                Map.of(
                        "place", place,
                        "placeType", PlaceType.values()
                )
        );
    }

    @GetMapping("/events")
    public ModelAndView adminEvents(@QuerydslPredicate(root = Event.class) Predicate predicate) {
        List<EventResponse> events = eventService.getEvents(predicate)
                .stream()
                .map(EventResponse::from)
                .toList();

        return new ModelAndView(
                "admin/events",
                Map.of(
                        "events", events,
                        "eventStatus", EventStatus.values()
                )
        );
    }

    @GetMapping("/events/{eventId}")
    public ModelAndView adminEventDetail(@PathVariable Long eventId) {
        EventResponse event = eventService.getEvent(eventId)
                .map(EventResponse::from)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));

        return new ModelAndView(
                "admin/event-detail",
                Map.of(
                        "event", event,
                        "eventStatus", EventStatus.values()
                )
        );
    }

}