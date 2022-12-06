package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.domain.Place;
import com.toyproject.lineupproject.domain.Post;

import java.time.LocalDateTime;
import java.util.Optional;

public record PostResponse(

        Long id,

        Long placeId,

        String placeName,

        String email,

        String nickName,

        String title,

        String post,

        LocalDateTime createAt,

        LocalDateTime modifiedAt

) {

    public static PostResponse of(Post post) {
        Place place = new Place();
        place.setIdForMock(0L);
        return new PostResponse(
                post.getId(),
                Optional.ofNullable(post.getPlace()).orElse(place).getId(),
                Optional.ofNullable(post.getPlace()).orElse(place).getPlaceName(),
                post.getAdmin().getEmail(),
                post.getAdmin().getNickname(),
                post.getTitle(),
                post.getPost(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }


}
