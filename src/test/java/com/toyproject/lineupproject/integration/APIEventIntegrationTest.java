package com.toyproject.lineupproject.integration;

import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.constant.EventStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
public class APIEventIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void givenParams_whenRequestingEvents_thenReturnsListOfEventsInStandardResponse() throws Exception {
        // Given

        // When & Then
        mvc.perform(
                        get("/api/events")
                                .queryParam("placeId", "1")
                                .queryParam("eventName", "운동")
                                .queryParam("eventStatus", EventStatus.OPENED.name())
                                .queryParam("eventStartDatetime", "2021-01-01T00:00:00")
                                .queryParam("eventEndDatetime", "2021-01-02T00:00:00")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()))
                .andDo(print());

    }
}
