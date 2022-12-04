package com.toyproject.lineupproject.service;

import com.querydsl.core.types.Predicate;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.constant.EventStatus;
import com.toyproject.lineupproject.domain.Event;
import com.toyproject.lineupproject.domain.Place;
import com.toyproject.lineupproject.dto.EventDto;
import com.toyproject.lineupproject.dto.EventViewResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.repository.AdminPlaceMapRepository;
import com.toyproject.lineupproject.repository.EventRepository;
import com.toyproject.lineupproject.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Transactional
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final PlaceRepository placeRepository;

    private final AdminPlaceMapRepository adminPlaceMapRepository;

    private final AdminService adminService;

    @Transactional(readOnly = true)
    public List<EventDto> getEvents(Predicate predicate) {
        try {
            return StreamSupport.stream(eventRepository.findAll(predicate).spliterator(), false)
                    .map(EventDto::of)
                    .toList();
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public Page<EventDto> getEventsByAdmin(String email, Predicate predicate, Pageable pageable) {
        try {
            Page<Event> all = eventRepository.findAll(predicate, pageable);
            if (!all.isEmpty()) {
                if (all.getContent().stream().filter(event ->
                                !(event.getPlace().getAdminEmail().equals(email)))
                        .collect(Collectors.toList()).size() != 0) {
                    return new PageImpl<>(new ArrayList<>(),
                            pageable,
                            0L);
                }
            }
            List<EventDto> eventDtos = StreamSupport.stream(
                            all.spliterator(), false)
                    .map(EventDto::of)
                    .toList();
            return new PageImpl<>(eventDtos, all.getPageable(), all.getTotalElements());
        } catch (GeneralException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public Page<EventDto> getEventsAll(Predicate predicate, Pageable pageable) {
        try {
            Page<Event> all = eventRepository.findAll(predicate, pageable);
            List<EventDto> eventDtos = StreamSupport.stream(
                            all.spliterator(), false)
                    .map(EventDto::of)
                    .toList();
            return new PageImpl<>(eventDtos, all.getPageable(), all.getTotalElements());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public Page<EventDto> getEventsAllByStatus(
            EventStatus status,
            Pageable pageable
    ) {
        try {
            Page<Event> all = eventRepository.findAllByEventStatus(status, pageable);
            List<EventDto> eventDtos = StreamSupport.stream(
                            all.spliterator(), false)
                    .map(EventDto::of)
                    .toList();
            return new PageImpl<>(eventDtos, all.getPageable(), all.getTotalElements());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public Page<EventDto> getEventsParams(
            HashMap<String, Object> param,
            Pageable pageable
    ) {
        try {
            String statusKey = (String) param.get("statusKey");
            EventStatus status = null;
            if (statusKey != null) {
                status = EventStatus.valueOf(statusKey.toUpperCase());
            }
            Page<Event> events = eventRepository.findEventPageBySearchParams(
                    (String) param.get("placeName"),
                    (String) param.get("eventName"),
                    status,
                    (LocalDateTime) param.get("eventStartDatetime"),
                    (LocalDateTime) param.get("eventEndDatetime"),
                    (String) param.get("email"),
                    pageable
            );
            List<EventDto> eventDtos = StreamSupport.stream(
                            events.spliterator(), false)
                    .map(EventDto::of)
                    .toList();
            return new PageImpl<>(eventDtos, events.getPageable(), events.getTotalElements());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }


    @Transactional(readOnly = true)
    public Page<EventViewResponse> getEventViewResponse(
            String placeName,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Pageable pageable
    ) {
        try {
            return eventRepository.findEventViewPageBySearchParams(
                    placeName,
                    eventName,
                    eventStatus,
                    eventStartDatetime,
                    eventEndDatetime,
                    pageable
            );
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<EventDto> getEvent(Long eventId) {
        try {
            return eventRepository.findById(eventId).map(EventDto::of);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public Page<EventViewResponse> getEvent(Long placeId, Pageable pageable) {
        try {
            Place place = placeRepository.findById(placeId)
                    .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));
            Page<Event> eventPage = eventRepository.findByPlace(place, pageable);

            return new PageImpl<>(
                    eventPage.getContent()
                            .stream()
                            .map(event -> EventViewResponse.from(EventDto.of(event)))
                            .toList(),
                    eventPage.getPageable(),
                    eventPage.getTotalElements()
            );
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean upsertEvent(EventDto eventDto) {
        try {
            if (eventDto.id() != null) {
                return modifyEvent(eventDto.id(), eventDto);
            } else {
                return createEvent(eventDto);
            }
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean createEvent(EventDto eventDto) {
        try {
            if (eventDto == null) {
                return false;
            }

            Place place = placeRepository.findById(eventDto.placeDto().id())
                    .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));
            eventRepository.save(eventDto.toEntity(place));
            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean modifyEvent(Long eventId, EventDto dto) {
        try {
            if (eventId == null || dto == null) {
                return false;
            }

            eventRepository.findById(eventId)
                    .ifPresent(event -> eventRepository.save(dto.updateEntity(event)));

            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean removeEvent(Long eventId) {
        try {
            if (eventId == null) {
                return false;
            }

            eventRepository.deleteById(eventId);
            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }
}