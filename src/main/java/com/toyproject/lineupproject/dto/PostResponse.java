package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.domain.Post;

import java.time.LocalDateTime;

public record PostResponse(

        Long id,

        Long placeId,

        String placeName,

        String email,

        String nickName,

        String title,

        String post,

        LocalDateTime createAt,

        LocalDateTime modifiedAt,

        Post.Status status


) {

    public static PostResponse of(Post post) {
        boolean equals = post.getStatus().equals(Post.Status.MESSAGE);
        String nickName = post.getAdmin().getNickname();
        if (equals) {
            nickName = "관리자";
        }
        return new PostResponse(
                post.getId(),
                post.getPlace().getId(),
                post.getPlace().getPlaceName(),
                post.getAdmin().getEmail(),
                nickName,
                post.getTitle(),
                post.getPost(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getStatus()
        );
    }


}
