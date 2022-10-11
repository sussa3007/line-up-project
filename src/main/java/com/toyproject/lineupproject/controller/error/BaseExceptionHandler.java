package com.toyproject.lineupproject.controller.error;


import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.exception.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

// View Excption Handler
@ControllerAdvice
public class BaseExceptionHandler {

    // GeneralException Handler
    @ExceptionHandler
    public ModelAndView general(GeneralException e){
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;

        return new ModelAndView(
                "error",
                Map.of(
                        "statusCode", status.value(),
                        "errorCode", errorCode,
                        "message", errorCode
                                .getMessage(e)
                ),
                status
        );
    }

    // Exception Handler
    @ExceptionHandler
    public ModelAndView exception(Exception e){
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ModelAndView(
                "error",
                Map.of(
                        "statusCode", status.value(),
                        "errorCode", errorCode,
                        "message", errorCode
                                .getMessage(e)
                ),
                status
        );
    }

}
