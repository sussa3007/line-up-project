package com.toyproject.lineupproject.repository;

import com.toyproject.lineupproject.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface EventRepository extends
        JpaRepository<Event, Long>,
        QuerydslPredicateExecutor<Event>
//        ,QuerydslBinderCustomizer<QEvent>
{

//    @Override
//    default void customize(QuerydslBindings bindings, QEvent root) {
//        bindings.excludeUnlistedProperties(true);
//        bindings.including(root.placeId, root.eventName, root.eventStatus, root.eventStartDatetime, root.eventEndDatetime);
//        bindings.bind(root.eventName).first(StringExpression::containsIgnoreCase);
//        bindings.bind(root.eventStartDatetime).first(ComparableExpression::goe);
//        bindings.bind(root.eventEndDatetime).first(ComparableExpression::loe);
//    }

}
