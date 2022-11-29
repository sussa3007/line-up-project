package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.domain.Admin;


public record AdminResponse(

        String email,


        String nickname,


        String password,


        String phoneNumber,


        String memo
) {
    public static AdminResponse of(
            String email,
            String nickname,
            String phoneNumber,
            String memo
    ) {
        return new AdminResponse(email, nickname, null, phoneNumber, memo);
    }
    public static AdminResponse of(
            Admin admin
    ) {
        return new AdminResponse(
                admin.getEmail(),
                admin.getNickname(),
                null,
                admin.getPhoneNumber(),
                admin.getMemo());
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