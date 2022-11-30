package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.domain.Admin;

import java.time.LocalDateTime;


public record AdminResponse(

        Long id,
        String email,

        String nickname,

        String password,

        String phoneNumber,

        String memo,

        String role,
        LocalDateTime createAt,
        String status,
        String loginBase
) {
    public static AdminResponse of(
            String email,
            String nickname,
            String phoneNumber,
            String memo
    ) {
        return new AdminResponse(null,email, nickname, null, phoneNumber, memo, null,null,null,null);
    }


    public static AdminResponse of(
            Admin admin
    ) {
        return new AdminResponse(
                admin.getId(),
                admin.getEmail(),
                admin.getNickname(),
                null,
                admin.getPhoneNumber(),
                admin.getMemo(),
                checkRole(admin),
                admin.getCreatedAt(),
                admin.getStatus().getMessage(),
                admin.getLoginBase().getMessage()
        );


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

    public static String checkRole(Admin admin) {
        String role = "";
        if (admin.getRoles().contains("SUPERADMIN")) {
            role = "SUPERADMIN";
        } else if (admin.getRoles().contains("ADMIN")) {
            role = "ADMIN";
        } else {
            role = "USER";
        }
        return role;
    }
}