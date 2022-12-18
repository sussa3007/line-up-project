package com.toyproject.lineupproject.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import com.toyproject.lineupproject.domain.AdminEventMap;
import com.toyproject.lineupproject.domain.QAdminEventMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface AdminEventMapRepository extends
        JpaRepository<AdminEventMap, Long>,
        QuerydslPredicateExecutor<AdminEventMap>,
        QuerydslBinderCustomizer<QAdminEventMap>
{
    @Override
    default void customize(QuerydslBindings bindings, QAdminEventMap root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.event.eventName, root.admin.email,
                root.admin.phoneNumber, root.event.place.address,root.event.place.placeName);
        bindings.bind(root.event.eventName).as("eventName").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.admin.email).as("email").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.admin.phoneNumber).as("phoneNumber").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.event.place.address).as("address").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.event.place.placeName).as("placeName").first(StringExpression::containsIgnoreCase);
    }

    Page<AdminEventMap> findAll(Predicate predicate, Pageable pageable);
}
