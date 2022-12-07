package com.toyproject.lineupproject.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.Place;
import com.toyproject.lineupproject.domain.Post;
import com.toyproject.lineupproject.domain.QPost;
import com.toyproject.lineupproject.repository.querydsl.PostRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface PostRepository extends JpaRepository<Post, Long>,
        PostRepositoryCustom,
        QuerydslPredicateExecutor<Post>,
        QuerydslBinderCustomizer<QPost> {

    @Override
    default void customize(QuerydslBindings bindings, QPost root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.place.placeName, root.title,
                root.post, root.admin.email);
        bindings.bind(root.place.placeName).as("placeName").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.post).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.admin.email).as("email").first(StringExpression::containsIgnoreCase);
    }

    Page<Post> findAll(Pageable pageable);
    Page<Post> findAll(Predicate predicate, Pageable pageable);

    Page<Post> findAllByAdmin(Pageable pageable, Admin admin);
    Page<Post> findAllByPlace(Pageable pageable, Place place);

    Page<Post> findAllByStatus(Post.Status status, Pageable pageable);

}
