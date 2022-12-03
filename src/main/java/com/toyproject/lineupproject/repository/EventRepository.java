package com.toyproject.lineupproject.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.toyproject.lineupproject.constant.EventStatus;
import com.toyproject.lineupproject.domain.Event;
import com.toyproject.lineupproject.domain.Place;
import com.toyproject.lineupproject.domain.QEvent;
import com.toyproject.lineupproject.repository.querydsl.EventRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.List;

public interface EventRepository extends
        JpaRepository<Event, Long>,
        EventRepositoryCustom,
        QuerydslPredicateExecutor<Event>,
        QuerydslBinderCustomizer<QEvent> {

    @Override
    default void customize(QuerydslBindings bindings, QEvent root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.place.placeName, root.place.adminEmail,root.eventName,
                root.eventStatus, root.eventStartDatetime, root.eventEndDatetime);
        bindings.bind(root.place.placeName).as("placeName").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.eventName).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.eventStartDatetime).first(ComparableExpression::goe);
        bindings.bind(root.eventEndDatetime).first(ComparableExpression::loe);
        bindings.bind(root.place.adminEmail).as("email").first(StringExpression::containsIgnoreCase);
    }

    Page<Event> findAll(Predicate predicate,Pageable pageable);
    Page<Event> findByPlace(Place place, Pageable pageable);
    List<Event> findAllByPlace(Place place);

    Page<Event> findAllByEventStatus(EventStatus status, Pageable pageable);
}
