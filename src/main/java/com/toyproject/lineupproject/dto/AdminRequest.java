package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.domain.Admin;
import io.micrometer.core.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


public record AdminRequest(
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
        message = "Please enter a valid email.")
        String email,

        @NotBlank
        String nickname,

        @NotBlank
        String password,

        @NotBlank
        String phoneNumber,

        @Nullable
        String memo
) {
    public static AdminRequest of(
            String email,
            String nickname,
            String password,
            String phoneNumber,
            String memo
    ) {
        return new AdminRequest(email, nickname, password, phoneNumber, memo);
    }
    public Admin dtoToAdmin() {
        return Admin.of(
                this.email(),
                this.nickname(),
                this.password(),
                this.phoneNumber(),
                this.memo()
        );
    }
}