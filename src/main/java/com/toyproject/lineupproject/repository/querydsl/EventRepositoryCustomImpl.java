package com.toyproject.lineupproject.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.constant.EventStatus;
import com.toyproject.lineupproject.domain.Event;
import com.toyproject.lineupproject.domain.QEvent;
import com.toyproject.lineupproject.dto.EventViewResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class EventRepositoryCustomImpl extends QuerydslRepositorySupport implements EventRepositoryCustom {

    public EventRepositoryCustomImpl() {
        super(Event.class);
    }

    @Override
    public Page<EventViewResponse> findEventViewPageBySearchParams(
            String placeName,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Pageable pageable
    ) {
        QEvent event = QEvent.event;

        JPQLQuery<EventViewResponse> query = from(event)
                .select(Projections.constructor(
                        EventViewResponse.class,
                        event.id,
                        event.place.adminEmail,
                        event.place.placeName,
                        event.eventName,
                        event.eventStatus,
                        event.eventStartDatetime,
                        event.eventEndDatetime,
                        event.currentNumberOfPeople,
                        event.capacity,
                        event.memo
                ));
        if (placeName != null && !placeName.isBlank()) {
            query.where(event.place.placeName.containsIgnoreCase(placeName));
        }

        if (eventName != null && !eventName.isBlank()) {
            query.where(event.eventName.containsIgnoreCase(eventName));
        }
        if (eventStatus != null) {
            query.where(event.eventStatus.eq(eventStatus));
        }
        if (eventStartDatetime != null) {
            query.where(event.eventStartDatetime.goe(eventStartDatetime));
        }
        if (eventEndDatetime != null) {
            query.where(event.eventEndDatetime.loe(eventEndDatetime));
        }

        List<EventViewResponse> events = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new GeneralException(
                        ErrorCode.DATA_ACCESS_ERROR,
                        "Spring Date JPA로 부터 QueryDsl 인스턴스를 받을수 없다."))
                .applyPagination(pageable, query)
                .fetch();
        return new PageImpl<>(events, pageable, query.fetchCount());
    }
    @Override
    public Page<Event> findEventPageBySearchParams(
            String placeName,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            String  email,
            Pageable pageable
    ) {
        QEvent event = QEvent.event;

        JPQLQuery<Event> query = from(event)
                .select(Projections.constructor(
                        Event.class,
                        event.id,
                        event.place,
                        event.eventName,
                        event.eventStatus,
                        event.eventStartDatetime,
                        event.eventEndDatetime,
                        event.currentNumberOfPeople,
                        event.capacity,
                        event.memo,
                        event.createdAt,
                        event.modifiedAt
                ));
        if (placeName != null && !placeName.isBlank()) {
            query.where(event.place.placeName.containsIgnoreCase(placeName));
        }
        if (email != null && !email.isBlank()) {
            query.where(event.place.adminEmail.containsIgnoreCase(email));
        }

        if (eventName != null && !eventName.isBlank()) {
            query.where(event.eventName.containsIgnoreCase(eventName));
        }
        if (eventStatus != null ) {
            query.where(event.eventStatus.eq(eventStatus));
        }
        if (eventStartDatetime != null ) {
            query.where(event.eventStartDatetime.goe(eventStartDatetime));
        }
        if (eventEndDatetime != null ) {
            query.where(event.eventEndDatetime.loe(eventEndDatetime));
        }

        List<Event> events = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new GeneralException(
                        ErrorCode.DATA_ACCESS_ERROR,
                        "Spring Date JPA로 부터 QueryDsl 인스턴스를 받을수 없다."))
                .applyPagination(pageable, query)
                .fetch();
        return new PageImpl<>(events, pageable, query.fetchCount());

    }
}

