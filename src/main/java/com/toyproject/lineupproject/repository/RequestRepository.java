package com.toyproject.lineupproject.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import com.toyproject.lineupproject.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface RequestRepository extends JpaRepository<Request, Long>,
        QuerydslPredicateExecutor<Request>,
        QuerydslBinderCustomizer<QRequest> {
//PageRequest.of(page, size, Sort.by("requestId").descending()));

    @Override
    default void customize(QuerydslBindings bindings, QRequest root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.admin.email,root.admin.nickname);
        bindings.bind(root.admin.email).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.admin.nickname).first(StringExpression::containsIgnoreCase);
//        bindings.bind(root.status).first();
    }
    Page<Request> findAll(Pageable pageable);
    Page<Request> findAll(Predicate predicate, Pageable pageable);

    Page<Request> findAllByAdmin(Pageable pageable, Admin admin);

}
