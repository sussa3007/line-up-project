package com.toyproject.lineupproject.repository;

import com.querydsl.core.BooleanBuilder;
import com.toyproject.lineupproject.constant.EventStatus;
import com.toyproject.lineupproject.constant.PlaceType;
import com.toyproject.lineupproject.domain.Event;
import com.toyproject.lineupproject.domain.Place;
import com.toyproject.lineupproject.dto.EventViewResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@DataJpaTest
class EventRepositoryTest {
    private final EventRepository sut;
    private final TestEntityManager testEntityManager;



    EventRepositoryTest(@Autowired EventRepository sut,
                        @Autowired TestEntityManager testEntityManager) {
        this.sut = sut;
        this.testEntityManager = testEntityManager;
    }

    @Test
    void givenSearchParams_whenFindingEventViewResponse_thenReturnEventViewResponsePage() {
        // Given

        // When
        Page<EventViewResponse> eventPage = sut.findEventViewPageBySearchParams(
                "배드민턴",
                "운동1",
                EventStatus.OPENED,
                LocalDateTime.of(2022, 8, 1, 0, 0, 0),
                LocalDateTime.of(2022, 8, 2, 0, 0, 0),
                PageRequest.of(0, 5)
        );
        // Then
        assertThat(eventPage.getTotalPages()).isEqualTo(1);
        assertThat(eventPage.getNumberOfElements()).isEqualTo(1);
        assertThat(eventPage.getContent().get(0))
                .hasFieldOrPropertyWithValue("placeName", "시민 배드민턴")
                .hasFieldOrPropertyWithValue("eventName", "운동1")
                .hasFieldOrPropertyWithValue("eventStatus", EventStatus.OPENED)
                .hasFieldOrPropertyWithValue("eventStartDatetime", LocalDateTime.of(2022, 8, 1, 9, 0, 0))
                .hasFieldOrPropertyWithValue("eventEndDatetime", LocalDateTime.of(2022, 8, 1, 12, 0, 0));

    }

    @DisplayName("이벤트 뷰 데이터 검색어에 따른 조회 결과가 없으면, 빈 데이터를 페이징 정보와 함께 리턴한다.")
    @Test
    void givenSearchParams_whenFindingNonexistentEventViewPage_thenReturnsEmptyEventViewResponsePage() {
        // Given

        // When
        Page<EventViewResponse> eventPage = sut.findEventViewPageBySearchParams(
                "없은 장소",
                "없는 이벤트",
                null,
                LocalDateTime.of(1000, 1, 1, 1, 1, 1),
                LocalDateTime.of(1000, 1, 1, 1, 1, 0),
                PageRequest.of(0, 5)
        );

        // Then
        assertThat(eventPage).hasSize(0);
    }

    @DisplayName("이벤트 뷰 데이터를 검색 파라미터 없이 페이징 값만 주고 조회하면, 전체 데이터를 페이징 처리하여 리턴한다.")
    @Test
    void givenPagingInfoOnly_whenFindingEventViewPage_thenReturnsEventViewResponsePage() {
        // Given

        // When
        Page<EventViewResponse> eventPage = sut.findEventViewPageBySearchParams(
                null,
                null,
                null,
                null,
                null,
                PageRequest.of(0, 5)
        );

        // Then
        assertThat(eventPage).hasSize(5);
    }

    @DisplayName("이벤트 뷰 데이터를 페이징 정보 없이 조회하면, 에러를 리턴한다.")
    @Test
    void givenNothing_whenFindingEventViewPage_thenThrowsError() {
        // Given

        // When
        Throwable t = catchThrowable(() -> sut.findEventViewPageBySearchParams(
                null,
                null,
                null,
                null,
                null,
                null
        ));

        // Then
        assertThat(t).isInstanceOf(InvalidDataAccessApiUsageException.class);
    }

    @DisplayName("JPA")
    @Test
    void test() {
        // Given
        Place place = createPlace();
        Event event = createEvent(place);
        testEntityManager.persist(place);
        testEntityManager.persist(event);
        // When
        Iterable<Event> events = sut.findAll(new BooleanBuilder());
        // Then
        assertThat(events).hasSize(7);
    }

    private Event createEvent(Place place) {

        return createEvent(
                place,
                "test",
                EventStatus.OPENED,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }


    private Event createEvent(
            Place place,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ) {
        return Event.of(
                place,
                eventName,
                eventStatus,
                eventStartDateTime,
                eventEndDateTime,
                0,
                24,
                "마스크 꼭 착용하세요"
        );

    }


    private Place createPlace() {
        return Place.of(PlaceType.COMMON, "test place", "test address", "010-1234-1234", 10, null);

    }

}