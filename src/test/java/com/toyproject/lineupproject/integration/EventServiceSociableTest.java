package com.toyproject.lineupproject.integration;


import com.toyproject.lineupproject.dto.EventDTO;
import com.toyproject.lineupproject.service.EventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest
public class EventServiceSociableTest {
    @Autowired
    private EventService sut;

    @Test
    @DisplayName("검색조건 없이 이벤트 검색하면, 전체 결과물을 보여준다")
    void givenNothing_whenSearchingEvents_thenReturnEntreEventList() {
        // Given

        // When
        List<EventDTO> list =
                sut.getEvents(null, null,
                        null, null, null);
        // Then
        assertThat(list).hasSize(0);
    }

}
