package com.toyproject.lineupproject.repository;

import com.querydsl.core.BooleanBuilder;
import com.toyproject.lineupproject.constant.EventStatus;
import com.toyproject.lineupproject.constant.PlaceType;
import com.toyproject.lineupproject.domain.Event;
import com.toyproject.lineupproject.domain.Place;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

@DataJpaTest
class EventRepositoryTest {
    private final EventRepository sut;
    private final TestEntityManager testEntityManager;


    EventRepositoryTest(@Autowired EventRepository sut, @Autowired TestEntityManager testEntityManager) {
        this.sut = sut;
        this.testEntityManager = testEntityManager;
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
        Assertions.assertThat(events).hasSize(27);
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