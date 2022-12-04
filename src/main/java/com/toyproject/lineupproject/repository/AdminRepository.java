package com.toyproject.lineupproject.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.QAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Collection;
import java.util.Optional;

public interface AdminRepository extends
        JpaRepository<Admin, Long>,
        QuerydslPredicateExecutor<Admin>,
        QuerydslBinderCustomizer<QAdmin>
{

    @Override
    default void customize(QuerydslBindings bindings, QAdmin root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.email, root.nickname,
                root.phoneNumber,root.createdAt);
        bindings.bind(root.email).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.nickname).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.phoneNumber).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).as("eventStartDatetime").first(ComparableExpression::goe);

    }
    Optional<Admin> findByEmail(String  email);
    Optional<Admin> findByNickname(String  nickname);

    Page<Admin> findAll(Pageable pageable);
    Page<Admin> findAll(Predicate predicate, Pageable pageable);

    Page<Admin> findAllByStatus(Admin.Status status,Pageable pageable);

    Page<Admin> findByRolesIn(Collection<String> roles, Pageable pageable);

}