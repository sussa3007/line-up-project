package com.toyproject.lineupproject.repository.querydsl;

import com.toyproject.lineupproject.dto.AdminEventResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface AdminEventRepositoryCustom {

    Page<AdminEventResponse> findAdminEventMapPageBySearchParams(
            String eventName,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            String memo,
            String email,
            String nickname,
            String phoneNumber,
            String placeName,
            String placeAddress,
            String placeAdminEmail,
            Pageable pageable
    );
}
