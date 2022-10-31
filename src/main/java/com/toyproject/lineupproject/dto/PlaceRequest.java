package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.constant.PlaceType;

public record PlaceRequest(
        PlaceType placeType,
        String placeName,
        String address,
        String phoneNumber,
        Integer capacity,
        String memo
) {
    public static PlaceRequest of(
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ) {
        return new PlaceRequest(
                placeType,
                placeName,
                address,
                phoneNumber,
                capacity,
                memo);
    }

    public PlaceDto toDto() {
        return PlaceDto.of(
                null,
                this.placeType(),
                this.placeName(),
                this.address(),
                this.phoneNumber(),
                this.capacity(),
                this.memo(),
                null,
                null
        );
    }

}
