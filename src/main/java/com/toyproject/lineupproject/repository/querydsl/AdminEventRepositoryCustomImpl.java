package com.toyproject.lineupproject.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.AdminEventMap;
import com.toyproject.lineupproject.domain.QAdminEventMap;
import com.toyproject.lineupproject.dto.AdminEventResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AdminEventRepositoryCustomImpl extends QuerydslRepositorySupport implements AdminEventRepositoryCustom {

    public AdminEventRepositoryCustomImpl() {
        super(AdminEventMap.class);
    }
    @Override
    public Page<AdminEventResponse> findAdminEventMapPageBySearchParams(
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
            Pageable pageable) {

        QAdminEventMap adminEventMap = QAdminEventMap.adminEventMap;
        JPQLQuery<AdminEventResponse> query = from(adminEventMap)
                .select(Projections.constructor(
                        AdminEventResponse.class,
                        adminEventMap.id,
                        adminEventMap.event.eventName,
                        adminEventMap.event.eventStartDatetime,
                        adminEventMap.event.eventEndDatetime,
                        adminEventMap.event.currentNumberOfPeople,
                        adminEventMap.event.capacity,
                        adminEventMap.requestNumberOfPeople,
                        adminEventMap.memo,
                        adminEventMap.admin.email,
                        adminEventMap.admin.nickname,
                        adminEventMap.admin.phoneNumber,
                        adminEventMap.event.place.placeName,
                        adminEventMap.event.place.phoneNumber,
                        adminEventMap.event.place.address,
                        adminEventMap.createdAt,
                        adminEventMap.modifiedAt
                ));


        if (eventName != null && !eventName.isBlank()) {
            query.where(adminEventMap.event.eventName.containsIgnoreCase(eventName));
        }
        if (eventStartDatetime != null) {
            query.where(adminEventMap.event.eventStartDatetime.goe(eventStartDatetime));
        }
        if (eventEndDatetime != null) {
            query.where(adminEventMap.event.eventEndDatetime.loe(eventEndDatetime));
        }
        if (memo != null && !memo.isBlank()) {
            query.where(adminEventMap.memo.containsIgnoreCase(memo));
        }
        if (email != null && !email.isBlank()) {
            query.where(adminEventMap.admin.email.containsIgnoreCase(email));
        }
        if (nickname != null && !nickname.isBlank()) {
            query.where(adminEventMap.admin.nickname.containsIgnoreCase(nickname));
        }
        if (phoneNumber != null && !phoneNumber.isBlank()) {
            query.where(adminEventMap.admin.phoneNumber.containsIgnoreCase(phoneNumber));
        }
        if (placeAddress != null && !placeAddress.isBlank()) {
            query.where(adminEventMap.event.place.address.containsIgnoreCase(placeAddress));
        }
        if (placeName != null && !placeName.isBlank()) {
            query.where(adminEventMap.event.place.placeName.containsIgnoreCase(placeName));
        }
        if (placeAdminEmail != null && !placeAdminEmail.isBlank()) {
            query.where(adminEventMap.event.place.adminEmail.containsIgnoreCase(placeAdminEmail));
        }

        List<AdminEventResponse> adminEventMaps = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new GeneralException(
                        ErrorCode.DATA_ACCESS_ERROR,
                        "Spring Date JPA로 부터 QueryDsl 인스턴스를 받을수 없다."))
                .applyPagination(pageable, query)
                .fetch();
        return new PageImpl<>(adminEventMaps, pageable, query.fetchCount());
    }
}
