package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.constant.RequestCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.Request;
import io.micrometer.core.lang.Nullable;

import javax.validation.constraints.NotBlank;

public record ReqRequest(

        @NotBlank
        String request,
        @NotBlank
        String email,

        @Nullable
        String message
) {
    public static ReqRequest of(
            String email,
            String request,
            String message
    ) {
        return new ReqRequest(email, request, message);
    }

    public Request dtoToRequest(Admin admin) {
        return Request.of(
                admin,
                RequestCode.valueOf(request),
                this.message,
                Request.Status.OPEN_ISSUE

        );
    }
    public Request dtoToRequest() {
        return Request.of(
                RequestCode.valueOf(request),
                this.message,
                Request.Status.OPEN_ISSUE
        );
    }

}
