package com.toyproject.lineupproject.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestCode {

    ADMIN_REQUEST("관리자 권한 요청"),
    USER_REQUEST("일반 회원 권한 요청"),
    USER_INACTIVE_REQUEST("회원 탈퇴 요청"),
    COMPLAIN_REQUEST("불만 사항 신고"),
    QUESTION_REQUEST("문의 사항 접수");


    private final String requestMessage;

}
