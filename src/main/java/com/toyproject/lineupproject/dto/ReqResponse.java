package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.constant.RequestCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.Request;

import java.time.LocalDateTime;

public record ReqResponse(

        Long id,

        String email,

        String request,

        String message,

        String status,

        LocalDateTime createAt,

        LocalDateTime modifiedAt
) {
    public static ReqResponse of(
            Long id,
            String email,
            String request,
            String message,
            String status,
            LocalDateTime createAt,
            LocalDateTime modifiedAt
    ) {
        return new ReqResponse(id,email, request, message,status,createAt,modifiedAt);
    }

    public static ReqResponse of(
            Request request
    ) {
        return new ReqResponse(
                request.getRequestId(),
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
                RequestCode.valueOf(request),
                message,
                Request.Status.valueOf(status)
        );
    }
}
