package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.domain.Admin;
import io.micrometer.core.lang.Nullable;

import javax.validation.constraints.NotBlank;


public record AdminRequest(
        @NotBlank
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