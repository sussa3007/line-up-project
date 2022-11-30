package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.constant.RequestCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.Request;
import io.micrometer.core.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;

public record ReqRequest(
        @NotBlank
        String email,

        @NotBlank
        String request,

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
                Arrays.stream(RequestCode.values())
                        .filter(c -> c.getRequestMessage().equals(request))
                        .findFirst().orElse(RequestCode.QUESTION_REQUEST),
                message,
                Request.Status.OPEN_ISSUE

        );
    }

}
