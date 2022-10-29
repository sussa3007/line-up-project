package com.toyproject.lineupproject.controller.error;

import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.dto.ApiErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class BaseErrorController implements ErrorController {

    // Error view
    @RequestMapping(path = "/error", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtmp(HttpServletResponse response){
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode =
                status.is4xxClientError()
                        ? ErrorCode.BAD_REQUEST
                        : ErrorCode.INTERNAL_ERROR;

        return new ModelAndView("error",
                Map.of(
                        "statusCode", status.value(),
                        "errorCode", errorCode,
                        "message", errorCode
                                .getMessage(status.getReasonPhrase())
                ),
                status
        );
    }

    // Error json body
    @RequestMapping("/error")
    public ResponseEntity<ApiErrorResponse> error(HttpServletResponse response){
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode =
                status.is4xxClientError()
                        ? ErrorCode.BAD_REQUEST
                        : ErrorCode.INTERNAL_ERROR;

        return ResponseEntity
                .status(status)
                .body(ApiErrorResponse.of(false,errorCode ));

    }

}
