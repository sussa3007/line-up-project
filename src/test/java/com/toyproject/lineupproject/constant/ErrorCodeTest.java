package com.toyproject.lineupproject.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ErrorCodeTest {

    @ParameterizedTest(name = "[{index}] {0} => {1}")
    @MethodSource
    @DisplayName("예외를 받으면, 예외 메세지가 포함된 메세지 출력")
    void givenExceptionMessage_whenGettingMessage_thenReturnsMessage(ErrorCode sut,String expected) {
        // Given
        Exception e = new Exception("This is test message");
        // When
        String result = sut.getMessage(e);
        // Then
        assertThat(result).isEqualTo(expected);
    }
    static Stream<Arguments> givenExceptionMessage_whenGettingMessage_thenReturnsMessage() {
        return Stream.of(
                arguments(ErrorCode.OK, "OK - This is test message"),
                arguments(ErrorCode.BAD_REQUEST, "Bad request - This is test message"),
                arguments(ErrorCode.SPRING_BAD_REQUEST, "Spring-detected bad request - This is test message"),
                arguments(ErrorCode.VALIDATION_ERROR, "Validation Error - This is test message"),
                arguments(ErrorCode.NOT_FOUND, "Requested resource is not found - This is test message."),
                arguments(ErrorCode.INTERNAL_ERROR, "Internal error - This is test message"),
                arguments(ErrorCode.SPRING_INTERNAL_ERROR, "Spring-detected internal error - This is test message"),
                arguments(ErrorCode.DATA_ACCESS_ERROR, "Data access error - This is test message")
        );
    }

    @ParameterizedTest(name = "[{index}] \"{0}\" => \"{1}\"")
    @MethodSource
    @DisplayName("에러 메세지를 받으면, 해당 에러 메세지를 출력")
    void givenMessage_whenGettingMessage_thenReturnsMessage(String input,String expected) {
        // Given
        // When
        String result = ErrorCode.INTERNAL_ERROR.getMessage(input);
        // Then
        assertThat(result).isEqualTo(expected);
    }
    static Stream<Arguments> givenMessage_whenGettingMessage_thenReturnsMessage() {
        return Stream.of(
                arguments(null,ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("",ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("   ",ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("This is test message","This is test message")
                );
    }

    @Test
    @DisplayName("toString() 호출 포맷")
    void givenErrorCode_whenGettingToString_thenReturnsToString() {
        // Given

        // When
        String result = ErrorCode.INTERNAL_ERROR.toString();
        // Then
        assertThat(result).isEqualTo("INTERNAL_ERROR (20000)");
    }



}