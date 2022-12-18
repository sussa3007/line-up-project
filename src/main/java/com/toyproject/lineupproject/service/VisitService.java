package com.toyproject.lineupproject.service;


import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.AdminEventMap;
import com.toyproject.lineupproject.domain.Event;
import com.toyproject.lineupproject.domain.Place;
import com.toyproject.lineupproject.dto.AdminEventRequest;
import com.toyproject.lineupproject.dto.AdminEventResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.repository.AdminEventMapRepository;
import com.toyproject.lineupproject.repository.AdminRepository;
import com.toyproject.lineupproject.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VisitService {

    private final AdminRepository adminRepository;

    private final EventRepository eventRepository;

    public final AdminEventMapRepository repository;


    public AdminEventResponse createVisit(
            Long eventId, AdminEventRequest request
    ) {
        Admin admin = adminRepository.findByEmail(request.adminEmail())
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND_MEMBER));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND_EVENT));
        AdminEventMap adminEvent = AdminEventMap.of(admin, event,request);
        adminEvent.addAdminEventMap();
        AdminEventMap save = repository.save(adminEvent);

        return AdminEventResponse.of(save);
    }
    @Transactional(readOnly = true)
    public AdminEventResponse findVisit(Long id) {
        AdminEventMap adminEventMap = repository.findById(id).orElseThrow(
                () -> new GeneralException(ErrorCode.NOT_FOUND_REQUEST));
        return AdminEventResponse.of(adminEventMap);
    }

    public AdminEventResponse modifyVisit(
            Long adminEventId, AdminEventRequest request
    ) {
        AdminEventMap adminEventMap = repository.findById(adminEventId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND_REQUEST));
        adminEventMap.updateEntity(request);
        return AdminEventResponse.of(adminEventMap);
    }

    public void deleteVisit(Long adminEventId) {
        AdminEventMap adminEventMap = repository.findById(adminEventId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND_REQUEST));
        adminEventMap.getEvent().modifyCurrentNumberOfPeople(adminEventMap.getRequestNumberOfPeople(),0);
        repository.delete(adminEventMap);
    }
    @Transactional(readOnly = true)
    public Page<AdminEventResponse> findVisitByAdmin(Admin admin, Pageable pageable) {
        Page<AdminEventMap> allByAdmin = repository.findAllByAdmin(admin, pageable);
        List<AdminEventResponse> all =allByAdmin.getContent()
                .stream()
                .map(AdminEventResponse::of)
                .collect(Collectors.toList());

        return new PageImpl<>(all, allByAdmin.getPageable(), allByAdmin.getTotalElements());
    }
    @Transactional(readOnly = true)
    public Page<AdminEventResponse> findVisitByPlace(Place place, Pageable pageable) {
        Page<AdminEventMap> allByPlace = repository.findAllByEvent_Place(place, pageable);
        List<AdminEventResponse> all =allByPlace.getContent()
                .stream()
                .map(AdminEventResponse::of)
                .collect(Collectors.toList());

        return new PageImpl<>(all, allByPlace.getPageable(), allByPlace.getTotalElements());
    }
    @Transactional(readOnly = true)
    public Page<AdminEventResponse> findVisitByEvent(Event event, Pageable pageable) {
        Page<AdminEventMap> allByEvent = repository.findAllByEvent(event, pageable);
        List<AdminEventResponse> all =allByEvent.getContent()
                .stream()
                .map(AdminEventResponse::of)
                .collect(Collectors.toList());

        return new PageImpl<>(all, allByEvent.getPageable(), allByEvent.getTotalElements());
    }
    @Transactional(readOnly = true)
    public Page<AdminEventResponse> findVisitByParams(
            Map<String ,Object> param,
            Pageable pageable
    ) {
        return getAdminEventMaps(param, pageable);
    }

    private Page<AdminEventResponse> getAdminEventMaps(Map<String, Object> param, Pageable pageable) {

        return repository.findAdminEventMapPageBySearchParams(
                (String) param.get("eventName"),
                (LocalDateTime) param.get("eventStartDatetime"),
                (LocalDateTime) param.get("eventEndDatetime"),
                (String) param.get("memo"),
                (String) param.get("email"),
                (String) param.get("nickname"),
                (String) param.get("phoneNumber"),
                (String) param.get("placeName"),
                (String) param.get("address"),
                (String) param.get("placeAdminEmail"),
                pageable
        );
    }
}
