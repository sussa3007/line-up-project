package com.toyproject.lineupproject.controller.api;


import com.toyproject.lineupproject.constant.EventStatus;
import com.toyproject.lineupproject.dto.APIDataResponse;
import com.toyproject.lineupproject.dto.EventRequest;
import com.toyproject.lineupproject.dto.EventResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api")
@RestController
public class APIEventController {

    @GetMapping("/events")
    public APIDataResponse<List<EventResponse>> getEvents() {
        return APIDataResponse.of(List.of(EventResponse.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        )));
    }

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    public APIDataResponse<Void> createEvent(@RequestBody EventRequest eventRequest) {
        return APIDataResponse.empty();
    }

    @GetMapping("/events/{eventId}")
    public APIDataResponse<EventResponse> getEvent(@PathVariable Long eventId) {
        if (eventId.equals(2L)) {
            return APIDataResponse.empty();
        }

        return APIDataResponse.of(EventResponse.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        ));
    }

    @PutMapping("/events/{eventId}")
    public APIDataResponse<Void> modifyEvent(
            @PathVariable Long eventId,
            @RequestBody EventRequest eventRequest
    ) {
        return APIDataResponse.empty();
    }

    @DeleteMapping("/events/{eventId}")
    public APIDataResponse<Void> removeEvent(@PathVariable Long eventId) {
        return APIDataResponse.empty();
    }


    // Local ExceptionHandler
//    @ExceptionHandler
//    public ResponseEntity<APIErrorResponse> general(GeneralException e){
//        ErrorCode errorCode = e.getErrorCode();
//        HttpStatus status = errorCode.isClientSideError() ?
//                HttpStatus.BAD_REQUEST :
//                HttpStatus.INTERNAL_SERVER_ERROR;
//        return ResponseEntity
//                .status(status)
//                .body(APIErrorResponse.of(
//                        false, errorCode, errorCode.getMessage(e)
//                ));
//    }
}
