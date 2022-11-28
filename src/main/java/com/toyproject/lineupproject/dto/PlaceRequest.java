package com.toyproject.lineupproject.dto;

import com.toyproject.lineupproject.constant.PlaceType;


public record PlaceRequest(
        Long id,
        String  adminEmail,
        PlaceType placeType,
        String placeName,
        String address,
        String phoneNumber,
        Integer capacity,
        String memo
) {


    public static PlaceRequest of(
            Long id,
            String adminEmail,
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ) {
        return new PlaceRequest(id, adminEmail,placeType, placeName, address, phoneNumber, capacity, memo);
    }

    public PlaceDto toDto() {
        return PlaceDto.of(
                this.id,
                this.adminEmail,
                this.placeType,
                this.placeName,
                this.address,
                this.phoneNumber,
                this.capacity,
                this.memo,
                null,
                null
        );
    }

}