package com.toyproject.lineupproject.controller.error;

import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.dto.APIErrorResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import javax.validation.ConstraintViolationException;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class APIExceptionHandlerTest {
    private APIExceptionHandler sut;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        sut = new APIExceptionHandler();
        webRequest = new DispatcherServletWebRequest(new MockHttpServletRequest());
    }

    @Test
    @DisplayName("검증 오류 - 응답 데이터 정의")
    void giveValidationException_whenCallingValidation_thenReturnsResponseEntity() {
        // Given
        ConstraintViolationException e = new ConstraintViolationException(Set.of());
        // When
        ResponseEntity<Object> response = sut.validation(e, webRequest);
        // Then
        assertThat(response)
                .hasFieldOrPropertyWithValue("body", APIErrorResponse.of(
                        false,
                        ErrorCode.VALIDATION_ERROR,e))
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("프로젝트 오류 - 응답 데이터 정의")
    void giveGeneralException_whenCallingValidation_thenReturnsResponseEntity() {
        // Given
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        GeneralException e = new GeneralException(errorCode);
        // When
        ResponseEntity<Object> response = sut.general(e, webRequest);
        // Then
        assertThat(response)
                .hasFieldOrPropertyWithValue("body", APIErrorResponse.of(
                        false,
                        errorCode,e))
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("기타 오류 - 응답 데이터 정의")
    void giveOtherException_whenCallingValidation_thenReturnsResponseEntity() {
        // Given
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        Exception e = new Exception();
        // When
        ResponseEntity<Object> response = sut.exception(e, webRequest);
        // Then
        assertThat(response)
                .hasFieldOrPropertyWithValue("body", APIErrorResponse.of(
                        false,
                        ErrorCode.INTERNAL_ERROR,e))
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}