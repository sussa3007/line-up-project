package com.toyproject.lineupproject.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Post;
import com.toyproject.lineupproject.domain.QPost;
import com.toyproject.lineupproject.dto.PostResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PostRepositoryCustomImpl extends QuerydslRepositorySupport implements PostRepositoryCustom{

    public PostRepositoryCustomImpl() {
        super(Post.class);
    }

    @Override
    public Page<PostResponse> findPostPageBySearchParams(
            String title,
            String post,
            String email,
            String placeName,
            Post.Status status,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Pageable pageable
    ) {

        QPost post1 = QPost.post1;

        JPQLQuery<PostResponse> query = from(post1)
                .select(Projections.constructor(
                        PostResponse.class,
                        post1.id,
                        post1.place.id,
                        post1.place.placeName,
                        post1.admin.email,
                        post1.admin.nickname,
                        post1.title,
                        post1.post,
                        post1.createdAt,
                        post1.modifiedAt,
                        post1.status
                ));
        if (title != null && !title.isBlank()) {
            query.where(post1.title.containsIgnoreCase(title));
        }
        if (post != null && !post.isBlank()) {
            query.where(post1.post.containsIgnoreCase(post));
        }
        if (email != null && !email.isBlank()) {
            query.where(post1.admin.email.containsIgnoreCase(email));
        }
        if (placeName != null && !placeName.isBlank()) {
            query.where(post1.place.placeName.containsIgnoreCase(placeName));
        }
        if (status != null) {
            query.where(post1.status.eq(status));
        }
        if (eventStartDatetime != null ) {
            query.where(post1.createdAt.goe(eventStartDatetime));
        }
        if (eventEndDatetime != null) {
            query.where(post1.createdAt.loe(eventEndDatetime));
        }
        List<PostResponse> posts = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new GeneralException(
                        ErrorCode.DATA_ACCESS_ERROR,
                        "Spring Date JPA로 부터 QueryDsl 인스턴스를 받을수 없다."))
                .applyPagination(pageable, query)
                .fetch();
        return new PageImpl<>(posts, pageable, query.fetchCount());
    }
}
