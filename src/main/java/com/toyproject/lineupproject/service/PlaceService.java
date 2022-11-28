package com.toyproject.lineupproject.service;

import com.querydsl.core.types.Predicate;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.AdminPlaceMap;
import com.toyproject.lineupproject.domain.Place;
import com.toyproject.lineupproject.dto.PlaceDto;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.repository.AdminPlaceMapRepository;
import com.toyproject.lineupproject.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceService {

    private final PlaceRepository placeRepository;

    private final AdminPlaceMapRepository adminPlaceMapRepository;

    private final AdminService adminService;

    // 전체조회
    @Transactional(readOnly = true)
    public List<PlaceDto> getPlaces(Predicate predicate) {
        try {
            return StreamSupport.stream(
                    placeRepository.findAll(predicate).spliterator(),
                            false)
                    .map(PlaceDto::of)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR);
        }
    }
    @Transactional(readOnly = true)
    public List<PlaceDto> getPlacesByEmail(String email) {
        try {
            Admin findUser = adminService.findUserByEmail(email);
            List<AdminPlaceMap> all = adminPlaceMapRepository.findAllByAdmin(findUser);
            return all.stream().map(a -> PlaceDto.of(a.getPlace()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR);
        }
    }
    @Transactional(readOnly = true)
    public List<PlaceDto> getPlacesAll() {
        try {
            return StreamSupport.stream(
                            placeRepository.findAll().spliterator(),
                            false)
                    .map(PlaceDto::of)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR);
        }
    }
    // 단건조회
    @Transactional(readOnly = true)
    public Optional<PlaceDto> getPlace(Long placeId) {
        try {
            return placeRepository.findById(placeId).map(PlaceDto::of);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR);
        }
    }

    public boolean upsertPlace(PlaceDto placeDto) {
        try {
            if (placeDto.id() != null) {
                return modifyPlace(placeDto.id(), placeDto);
            } else {

                return createPlace(placeDto);
            }
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    // 만들기
    public boolean createPlace(PlaceDto placeDto) {
        try {
            if(placeDto==null){ return false; }
            Place place = placeRepository.save(placeDto.toEntity());
            Admin admin = adminService.findUserByEmail(placeDto.adminEmail());
            place.addAdminPlaceMaps(admin);
            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR);
        }
    }
    // 수정
    public boolean modifyPlace(Long placeId, PlaceDto placeDto) {
        try {
            if(placeId==null||placeDto==null){
                return false;
            }
            placeRepository.findById(placeId)
                    .ifPresent(
                            place -> placeRepository.save(placeDto.updateEntity(place)
                            ));
            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR);
        }
    }

    // 삭제
    public boolean removePlace(Long placeId) {
        try {
            if (placeId == null) {
                return false;
            }
            Place place = placeRepository.findById(placeId)
                    .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));
            if (place.getEvents().size() == 0) {
                placeRepository.deleteById(placeId);
            } else throw new GeneralException(ErrorCode.REQUETS_DELETE_PLACE_DENIED);
            return true;
        } catch (GeneralException e) {
            throw new GeneralException(ErrorCode.REQUETS_DELETE_PLACE_DENIED);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR);
        }
    }
}
