package com.toyproject.lineupproject.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.AdminPlaceMap;
import com.toyproject.lineupproject.domain.QAdminPlaceMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.List;

public interface AdminPlaceMapRepository extends
        JpaRepository<AdminPlaceMap, Long>,
        QuerydslPredicateExecutor<AdminPlaceMap>,
        QuerydslBinderCustomizer<QAdminPlaceMap>
{

    @Override
    default void customize(QuerydslBindings bindings, QAdminPlaceMap root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.place.placeName, root.admin.email,
                root.place.phoneNumber, root.place.address);
        bindings.bind(root.place.placeName).as("placeName").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.admin.email).as("email").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.place.phoneNumber).as("phoneNumber").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.place.address).as("address").first(StringExpression::containsIgnoreCase);
    }
    List<AdminPlaceMap> findAllByAdmin(Admin user);
    Page<AdminPlaceMap> findAllByAdmin(Admin user,Predicate predicate, Pageable pageable);
    Page<AdminPlaceMap> findAll(Predicate predicate, Pageable pageable);
}