package com.toyproject.lineupproject.dto;

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

    public Post dtoToPost() {
        return Post.of(
                null,
                null,
                this.title,
                this.post,
                this.password,
                Post.Status.valueOf(status)
        );
    }

}
