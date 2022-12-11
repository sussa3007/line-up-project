package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.domain.Place;
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
        Place postPlace = post.getPlace();
        Long placeId = null;
        String placeName = null;
        if (postPlace != null) {
            placeId = postPlace.getId();
            placeName = postPlace.getPlaceName();
        }
        return new PostResponse(
                post.getId(),
                placeId,
                placeName,
                post.getAdmin().getEmail(),
                post.getAdmin().getNickname(),
                post.getTitle(),
                post.getPost(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getStatus()
        );
    }


}
