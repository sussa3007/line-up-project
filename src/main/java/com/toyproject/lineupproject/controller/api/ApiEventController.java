package com.toyproject.lineupproject.controller.api;


import com.toyproject.lineupproject.constant.EventStatus;
import com.toyproject.lineupproject.constant.PlaceType;
import com.toyproject.lineupproject.dto.ApiDataResponse;
import com.toyproject.lineupproject.dto.EventRequest;
import com.toyproject.lineupproject.dto.EventResponse;
import com.toyproject.lineupproject.dto.PlaceDto;
import com.toyproject.lineupproject.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Deprecated
@RequiredArgsConstructor
//@Validated
//@RequestMapping("/api")
//@RestController
public class ApiEventController {

    private final EventService eventService;

    @GetMapping("/events")
    public ApiDataResponse<List<EventResponse>> getEvents(
            @Positive Long placeId,
            @Size(min = 2) String eventName,
            EventStatus eventStatus,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDatetime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDatetime
    ) {
        return ApiDataResponse.of(List.of(EventResponse.of(
                1L,
                PlaceDto.of(
                        1L,
                        PlaceType.SPORTS,
                        "배드민턴장",
                        "서울시 가나구 다라동",
                        "010-1111-2222",
                        0,
                        null,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ),
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        )));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/place/{placeId}/events")
    public ApiDataResponse<String> createEvent(
            @Valid @RequestBody EventRequest eventRequest,
            @PathVariable Long placeId
    ) {
        boolean result = eventService.createEvent(eventRequest.toDto(PlaceDto.idOnly(placeId)));

        return ApiDataResponse.of(Boolean.toString(result));
    }

    @GetMapping("/events/{eventId}")
    public ApiDataResponse<EventResponse> getEvent(@Positive @PathVariable Long eventId) {
        EventResponse eventResponse = EventResponse.from(eventService.getEvent(eventId).orElse(null));

        return ApiDataResponse.of(eventResponse);
    }

    @PutMapping("/events/{eventId}")
    public ApiDataResponse<String> modifyEvent(
            @Positive @PathVariable Long eventId,
            @Valid @RequestBody EventRequest eventRequest
    ) {
        boolean result = eventService.modifyEvent(eventId, eventRequest.toDto(null));
        return ApiDataResponse.of(Boolean.toString(result));
    }

    @DeleteMapping("/events/{eventId}")
    public ApiDataResponse<String> removeEvent(@Positive @PathVariable Long eventId) {
        boolean result = eventService.removeEvent(eventId);

        return ApiDataResponse.of(Boolean.toString(result));
    }

}