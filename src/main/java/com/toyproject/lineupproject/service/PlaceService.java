package com.toyproject.lineupproject.service;

import com.querydsl.core.types.Predicate;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.dto.PlaceDto;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    // 전체조회
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
    // 단건조회
    public Optional<PlaceDto> getPlace(Long placeId) {
        try {
            return placeRepository.findById(placeId).map(PlaceDto::of);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR);
        }
    }
    // 만들기
    public boolean createPlace(PlaceDto placeDto) {
        try {
            if(placeDto==null){ return false; }
            placeRepository.save(placeDto.toEntity());
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
            placeRepository.deleteById(placeId);
            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR);
        }
    }
}
