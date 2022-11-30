package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.constant.RequestCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.Request;

import java.time.LocalDateTime;
import java.util.Arrays;

public record ReqResponse(

        String email,

        String request,

        String message,

        String status,

        LocalDateTime createAt,

        LocalDateTime modifiedAt
) {
    public static ReqResponse of(
            String email,
            String request,
            String message,
            String status,
            LocalDateTime createAt,
            LocalDateTime modifiedAt
    ) {
        return new ReqResponse(email, request, message,status,createAt,modifiedAt);
    }

    public static ReqResponse of(
            Request request
    ) {
        return new ReqResponse(
                request.getAdmin().getEmail(),
                request.getRequestCode().getRequestMessage(),
                request.getMessage(),
                request.getStatus().getMessage(),
                request.getCreatedAt(),
                request.getModifiedAt()
        );
    }

    public Request dtoToRequest(Admin admin) {
        return Request.of(
                admin,
                Arrays.stream(RequestCode.values())
                        .filter(c -> c.getRequestMessage().equals(request))
                        .findFirst().orElse(RequestCode.QUESTION_REQUEST),
                message,
                Arrays.stream(Request.Status.values())
                        .filter(s -> s.getMessage().equals(status))
                        .findFirst().orElse(Request.Status.IN_PROGRESS_ISSUE)

        );
    }
}
