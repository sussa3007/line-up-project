package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.constant.PlaceType;

public record PlaceResponse(
        Long id,
        String adminEmail,
        PlaceType placeType,
        String placeName,
        String address,
        String phoneNumber,
        Integer capacity,
        String memo
) {
    public static PlaceResponse of(
            Long id,
            String adminEmail,
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ) {
        return new PlaceResponse(
                id,
                adminEmail,
                placeType,
                placeName,
                address,
                phoneNumber,
                capacity,
                memo);
    }
    public static PlaceResponse from(PlaceDto placeDto) {
        if (placeDto == null) { return null; }
        return PlaceResponse.of(
                placeDto.id(),
                placeDto.adminEmail(),
                placeDto.placeType(),
                placeDto.placeName(),
                placeDto.address(),
                placeDto.phoneNumber(),
                placeDto.capacity(),
                placeDto.memo()
        );
    }
}