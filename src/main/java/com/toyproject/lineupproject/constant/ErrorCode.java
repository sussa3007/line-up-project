package com.toyproject.lineupproject.constant;

import com.toyproject.lineupproject.exception.GeneralException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    OK(0, HttpStatus.OK, "Ok"),

    BAD_REQUEST(10000, HttpStatus.BAD_REQUEST, "Bad request"),
    SPRING_BAD_REQUEST(10001, HttpStatus.BAD_REQUEST, "Spring-detected bad request"),
    VALIDATION_ERROR(10002, HttpStatus.BAD_REQUEST, "Validation error"),
    NOT_FOUND(10003, HttpStatus.NOT_FOUND, "Requested resource is not found"),
    NOT_FOUND_COOKIE(10004, HttpStatus.NOT_FOUND, "Not Found Cookie"),
    NOT_FOUND_MEMBER(10005, HttpStatus.NOT_FOUND, "Not Found Member"),
    MEMBER_EXISTS(10006, HttpStatus.BAD_REQUEST, "Member Exists"),
    NICKNAME_EXISTS(10006, HttpStatus.BAD_REQUEST, "Nickname Exists"),

    REQUETS_DELETE_PLACE_DENIED(10007, HttpStatus.BAD_REQUEST, "Request Place Delete Denied"),

    INTERNAL_ERROR(20000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    SPRING_INTERNAL_ERROR(20001, HttpStatus.INTERNAL_SERVER_ERROR, "Spring-detected internal error"),
    DATA_ACCESS_ERROR(20002, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error"),
    EXPIRED_ACCESS_TOKEN(30004,HttpStatus.FORBIDDEN ,"EXPIRED ACCESS TOKEN"),
    EXPIRED_REFRESH_TOKEN(30005,HttpStatus.FORBIDDEN ,"EXPIRED REFRESH TOKEN"),
    UNAUTHORIZED_ACCESS(30006, HttpStatus.BAD_REQUEST,"UNAUTHORIZED ACCESS"),
    ACCESS_DENIED(30007, HttpStatus.FORBIDDEN,"Access Denied"),
    OAUTH2_ACCESS_ERROR(30008, HttpStatus.INTERNAL_SERVER_ERROR, "OAuth2 Access Error")
    ;

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;


    public static ErrorCode valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) { throw new GeneralException("HttpStatus is null."); }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if (httpStatus.is4xxClientError()) { return ErrorCode.BAD_REQUEST; }
                    else if (httpStatus.is5xxServerError()) { return ErrorCode.INTERNAL_ERROR; }
                    else { return ErrorCode.OK; }
                });
    }

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }

}
