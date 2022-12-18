package com.toyproject.lineupproject.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record AdminEventRequest(
        @NotBlank
        String eventName,
        @NotBlank
        String adminEmail,
        @NotNull
        Integer requestNumberOfPeople,
        String memo
) {




}
