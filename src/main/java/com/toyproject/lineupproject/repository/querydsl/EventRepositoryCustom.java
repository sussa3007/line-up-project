package com.toyproject.lineupproject.repository.querydsl;

import com.toyproject.lineupproject.constant.EventStatus;
import com.toyproject.lineupproject.domain.Event;
import com.toyproject.lineupproject.dto.EventDto;
import com.toyproject.lineupproject.dto.EventViewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface EventRepositoryCustom {
    Page<EventViewResponse> findEventViewPageBySearchParams(
            String placeName,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Pageable pageable
    );

    Page<Event> findEventPageBySearchParams(
            String placeName,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            String adminEmail,
            Pageable pageable
    );

}
