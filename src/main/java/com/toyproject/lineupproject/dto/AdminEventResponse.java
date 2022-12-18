package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.domain.AdminEventMap;

import java.time.LocalDateTime;

public record AdminEventResponse(
        Long id,
        String eventName,
        LocalDateTime eventStartDatetime,
        LocalDateTime eventEndDatetime,
        Integer currentNumberOfPeople,
        Integer capacity,
        Integer requestNumberOfPeople,
        String memo,
        String email,
        String nickname,
        String phoneNumber,
        String placeName,
        String placePhoneNumber,
        String placeAddress,
        LocalDateTime createAt,
        LocalDateTime modifyAt
) {

    public static AdminEventResponse of (AdminEventMap adminEvent) {
        return new AdminEventResponse(
                adminEvent.getId(),
                adminEvent.getEvent().getEventName(),
                adminEvent.getEvent().getEventStartDatetime(),
                adminEvent.getEvent().getEventEndDatetime(),
                adminEvent.getEvent().getCurrentNumberOfPeople(),
                adminEvent.getEvent().getCapacity(),
                adminEvent.getRequestNumberOfPeople(),
                adminEvent.getMemo(),
                adminEvent.getAdmin().getEmail(),
                adminEvent.getAdmin().getNickname(),
                adminEvent.getAdmin().getPhoneNumber(),
                adminEvent.getEvent().getPlace().getPlaceName(),
                adminEvent.getEvent().getPlace().getPhoneNumber(),
                adminEvent.getEvent().getPlace().getAddress(),
                adminEvent.getCreatedAt(),
                adminEvent.getModifiedAt()
        );
    }

}
