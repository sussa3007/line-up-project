package com.toyproject.lineupproject.service;

import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.constant.EventStatus;
import com.toyproject.lineupproject.dto.EventDTO;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public List<EventDTO> getEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDatetime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDatetime
    ) {
        try {
            return eventRepository
                    .findEvents(placeId,eventName,eventStatus,eventStartDatetime,eventEndDatetime);
        } catch (Exception e){
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR,e);
        }

    }

    public Optional<EventDTO> getEvent(Long eventId) {
        try {
            return eventRepository.findEvent(eventId);
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean createEvent(EventDTO eventDTO) {
        try {
            return eventRepository.insertEvent(eventDTO);
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean modifyEvent(Long eventId, EventDTO eventDTO) {
        try {
            return eventRepository.updateEvent(eventId, eventDTO);
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean removeEvent(Long eventId) {
        try {
            return eventRepository.deleteEvent(eventId);
        }
        catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }


}
