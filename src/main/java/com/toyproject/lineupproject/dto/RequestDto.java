package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.domain.Request;

public record RequestDto(
        String request,


        String email,

        String message,

        String adminMessage,

        String status

) {
    public static RequestDto of(
            String email,
            String request,
            String message,
            String status
    ) {
        return new RequestDto(email, request, message,null,status);
    }

    public static RequestDto of(
            Request request
    ) {
        return new RequestDto(
                request.getAdmin().getEmail(),
                request.getRequestCode().getRequestMessage(),
                request.getMessage(),
                null,
                request.getStatus().getMessage()
        );
    }

    public Request dtoToRequest() {
        return Request.of(
                message+'\n'+adminMessage,
                Request.Status.valueOf(status)
        );
    }
}
