package com.toyproject.lineupproject.repository.querydsl;

import com.toyproject.lineupproject.domain.Post;
import com.toyproject.lineupproject.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface PostRepositoryCustom {

    Page<PostResponse> findPostPageBySearchParams(
            String title,
            String post,
            String  email,
            String  placeName,
            Post.Status status,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Pageable pageable
    );
}
