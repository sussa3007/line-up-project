package com.toyproject.lineupproject.controller.error;


import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.dto.APIErrorResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import javax.validation.ConstraintViolationException;


// API Excption Handler
@RestControllerAdvice(annotations = {RestController.class, RepositoryRestController.class})
// RestController를 사용하는 API만 적용
// Spring webMVC에서 발생하는 Exception 추가 ResponseEntityExceptionHandler 상속 받음
// handleExceptionInternal 오버라이딩
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request){
        return getInternalResponseEntity(e,
                ErrorCode.VALIDATION_ERROR,
                HttpHeaders.EMPTY,
                HttpStatus.BAD_REQUEST,
                request);
    }



    private ResponseEntity<Object> getInternalResponseEntity(ResponseEntity<Object> e) {
        return e;
    }

    // GeneralException Handler
    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e,WebRequest request){
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;
        return getInternalResponseEntity(e, errorCode,HttpHeaders.EMPTY, status, request);
    }

    // Exception Handler
    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request){
        return getInternalResponseEntity(
                e,
                ErrorCode.INTERNAL_ERROR,
                HttpHeaders.EMPTY,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception e,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ErrorCode errorCode = status.is4xxClientError() ?
                ErrorCode.SPRING_BAD_REQUEST :
                ErrorCode.SPRING_INTERNAL_ERROR;
        return getInternalResponseEntity(e, errorCode,headers, status, request);
    }
    private ResponseEntity<Object> getInternalResponseEntity(
            Exception e,
            ErrorCode errorCode,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return getInternalResponseEntity(super.handleExceptionInternal(
                e,
                APIErrorResponse.of(
                        false,
                        errorCode.getCode(),
                        errorCode.getMessage(e)
                ),
                HttpHeaders.EMPTY,
                status,
                request
        ));
    }
}
