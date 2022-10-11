package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.constant.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class APIDataResponseTest {

    @Test
    @DisplayName("문자열 데이터가 주어지면, 표준 성공 응답 생성")
    void givenStringData_whenCreatingResponse_thenReturnSuccessfulResponse() {
        // Given
        String data = "test data";
        // When
        APIDataResponse<String> response = APIDataResponse.of(data);

        // Then
        assertThat(response)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OK.getCode())
                .hasFieldOrPropertyWithValue("message", ErrorCode.OK.getMessage())
                .hasFieldOrPropertyWithValue("data", data);
    }

    @Test
    @DisplayName("데이터가 없을때 비어있는 표준 성공 응답 생성")
    void givenNothing_whenCreatingResponse_thenReturnEmptyResponse() {
        // Given
        // When
        APIDataResponse<String> response = APIDataResponse.empty();

        // Then
        assertThat(response)
                .hasFieldOrPropertyWithValue("success", true)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OK.getCode())
                .hasFieldOrPropertyWithValue("message", ErrorCode.OK.getMessage())
                .hasFieldOrPropertyWithValue("data", null);
    }

}