package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.Place;
import com.toyproject.lineupproject.domain.Post;

import javax.validation.constraints.NotBlank;

public record PostRequest(

        @NotBlank
        String email,

        @NotBlank
        String title,

        @NotBlank
        String post,

        @NotBlank
        String password,

        @NotBlank
        String status

) {
    public static PostRequest of(
            String email,
            String title,
            String post,
            String password,
            String status
    ) {
        return new PostRequest(email,title, post, password, status);
    }

    public Post dtoToPost(Admin admin) {
        return Post.of(
                admin,
                this.title,
                this.post,
                this.password,
                Post.Status.valueOf(status)
        );
    }
    public Post dtoToPost(Admin admin, Place place) {
        return Post.of(
                admin,
                place,
                this.title,
                this.post,
                this.password,
                Post.Status.valueOf(status)
        );
    }

}
