package com.toyproject.lineupproject.controller.error;


import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.dto.APIErrorResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

// API Excption Handler
@RestControllerAdvice(annotations = RestController.class)
// RestController를 사용하는 API만 적용
// Spring webMVC에서 발생하는 Exception 추가 ResponseEntityExceptionHandler 상속 받음
// handleExceptionInternal 오버라이딩
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    // GeneralException Handler
    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e,WebRequest request){
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;
        return super.handleExceptionInternal(e,
                APIErrorResponse.of(
                        false,
                        errorCode.getCode(),
                        errorCode.getMessage(e)
                ),
                HttpHeaders.EMPTY,
                status,
                request
        );
        // ResponseEntityExceptionHandler를 상속 받으며,
        // handleExceptionInternal 상속 메소드를 사용함
//        return ResponseEntity
//                .status(status)
//                .body(APIErrorResponse.of(
//                        false, errorCode, errorCode.getMessage(e)
//                ));
    }

    // Exception Handler
    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request){
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return super.handleExceptionInternal(e,
                APIErrorResponse.of(
                        false,
                        errorCode.getCode(),
                        errorCode.getMessage(e)
                ),
                HttpHeaders.EMPTY,
                status,
                request
        );
//        return ResponseEntity
//                .status(status)
//                .body(APIErrorResponse.of(
//                        false, errorCode, errorCode.getMessage(e)
//                ));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ErrorCode errorCode = status.is4xxClientError() ?
                ErrorCode.SPRING_BAD_REQUEST :
                ErrorCode.SPRING_INTERNAL_ERROR;
        return super.handleExceptionInternal(ex,
                APIErrorResponse.of(
                        false,
                        errorCode.getCode(),
                        errorCode.getMessage(ex)
                ),
                headers,
                status,
                request
        );
    }
}
