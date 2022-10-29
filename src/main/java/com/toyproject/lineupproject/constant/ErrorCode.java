package com.toyproject.lineupproject.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
//    OK(0, HttpStatus.OK, "Ok"),
//
//    BAD_REQUEST(10000, HttpStatus.BAD_REQUEST, "Bad request"),
//    SPRING_BAD_REQUEST(10001, HttpStatus.BAD_REQUEST, "Spring-detected bad request"),
//    VALIDATION_ERROR(10002, HttpStatus.BAD_REQUEST, "Validation error"),
//    NOT_FOUND(10003, HttpStatus.NOT_FOUND, "Requested resource is not found"),
//
//    INTERNAL_ERROR(20000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
//    SPRING_INTERNAL_ERROR(20001, HttpStatus.INTERNAL_SERVER_ERROR, "Spring-detected internal error"),
//    DATA_ACCESS_ERROR(20002, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error")
//    ;

    OK(0,ErrorCategory.NORMAL, "OK"),

    BAD_REQUEST(10000, ErrorCategory.CLIENT_SIDE, "Bad request"),
    SPRING_BAD_REQUEST(10001, ErrorCategory.CLIENT_SIDE, "Spring-detected bad request"),
    VALIDATION_ERROR(10002, ErrorCategory.CLIENT_SIDE, "Validation Error"),
    NOT_FOUND(10003, ErrorCategory.CLIENT_SIDE, "Requested resource is not found"),

    INTERNAL_ERROR(20000, ErrorCategory.SERVER_SIDE, "Internal error"),
    SPRING_INTERNAL_ERROR(20001, ErrorCategory.SERVER_SIDE, "Spring-detected internal error"),
    DATA_ACCESS_ERROR(20002,ErrorCategory.SERVER_SIDE, "Data access error")
    ;

    private final Integer code;
    private final ErrorCategory errorCategory;
    private final String message;
    public enum ErrorCategory{
        NORMAL, CLIENT_SIDE, SERVER_SIDE
    }

    public String getMessage(Exception e) {
        return getMessage(this.getMessage() + " - " + e.getMessage());
    }
    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }
    public boolean isClientSideError() {
        return this.getErrorCategory() == ErrorCategory.CLIENT_SIDE;
    }

    public boolean isServerIsdeError() {
        return this.getErrorCategory() == ErrorCategory.SERVER_SIDE;
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }




}
