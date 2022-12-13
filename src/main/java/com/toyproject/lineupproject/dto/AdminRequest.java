package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.auth.jwt.utils.JwtAuthorityUtils;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.exception.GeneralException;
import io.micrometer.core.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Arrays;


public record AdminRequest(
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
                message = "정확한 이메일을 입력해 주세요.")
        String email,

        @NotBlank
        String nickname,

        String password,

        @NotBlank
        String phoneNumber,

        @Nullable
        String memo,

        @Nullable
        String role,

        @Nullable
        String status

) {
    public static AdminRequest of(
            String email,
            String nickname,
            String password,
            String phoneNumber,
            String memo
    ) {
        return new AdminRequest(email, nickname, password, phoneNumber, memo,null,null);
    }

    public static AdminRequest of(
            String email,
            String nickname,
            String password,
            String phoneNumber,
            String memo,
            String role,
            String status
    ) {
        return new AdminRequest(email, nickname, password, phoneNumber, memo,role ,status);
    }

    public Admin dtoToAdmin() {
        return Admin.of(
                this.email(),
                this.nickname(),
                this.password(),
                this.phoneNumber(),
                this.memo(),
                null,
                null
        );
    }
    public Admin dtoToAdminFull() {
        Admin of = Admin.of(
                this.email(),
                this.nickname(),
                this.password(),
                this.phoneNumber(),
                this.memo(),
                null,
                null
        );
        Admin.Status findStatus = Arrays.stream(Admin.Status.values())
                .filter(a -> a.getMessage().equals(this.status))
                .findFirst().orElse(Admin.Status.ACTIVE_USER);
        of.setStatus(findStatus);

        if (JwtAuthorityUtils.USER_ROLES_STRINGS.contains(this.role)) {
            of.setRoles(JwtAuthorityUtils.USER_ROLES_STRINGS);
        } else if (JwtAuthorityUtils.ADMIN_ROLES_STRINGS.contains(this.role)) {
            of.setRoles(JwtAuthorityUtils.ADMIN_ROLES_STRINGS);
        } else if (JwtAuthorityUtils.SUPER_ADMIN_ROLES_STRINGS.contains(this.role)) {
            of.setRoles(JwtAuthorityUtils.SUPER_ADMIN_ROLES_STRINGS);
        } else {
            throw new GeneralException(ErrorCode.BAD_REQUEST);
        }

        return of;
    }
}