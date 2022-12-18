package com.toyproject.lineupproject.service;


import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.AdminEventMap;
import com.toyproject.lineupproject.domain.Event;
import com.toyproject.lineupproject.dto.AdminEventRequest;
import com.toyproject.lineupproject.dto.AdminEventResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.repository.AdminEventMapRepository;
import com.toyproject.lineupproject.repository.AdminRepository;
import com.toyproject.lineupproject.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
