package com.toyproject.lineupproject.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.toyproject.lineupproject.domain.Place;
import com.toyproject.lineupproject.domain.QPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface PlaceRepository extends
        JpaRepository<Place, Long>,
        QuerydslPredicateExecutor<Place>,
        QuerydslBinderCustomizer<QPlace>
{

    @Override
    default void customize(QuerydslBindings bindings, QPlace root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.placeName, root.address, root.phoneNumber);
        bindings.bind(root.placeName).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.address).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.phoneNumber).first(StringExpression::containsIgnoreCase);
    }
}
