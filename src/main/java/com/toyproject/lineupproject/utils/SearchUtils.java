package com.toyproject.lineupproject.utils;

import com.toyproject.lineupproject.dto.EventDto;
import com.toyproject.lineupproject.dto.EventResponse;
import com.toyproject.lineupproject.dto.PlaceDto;
import com.toyproject.lineupproject.dto.PlaceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class SearchUtils {

    public String getSearchUri(HashMap<String, Object> param) {
        String eventStartDatetimeDecode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("eventStartDatetime"))
                                .orElse(""), StandardCharsets.UTF_8);
        String eventEndDatetimeDecode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("eventEndDatetime"))
                                .orElse(""), StandardCharsets.UTF_8);
        String kewWordDecode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("kewWord"))
                                .orElse(""), StandardCharsets.UTF_8);
        String searchWordDecode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("searchWord"))
                                .orElse(""), StandardCharsets.UTF_8);
        String pageDecode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("page"))
                                .orElse(""), StandardCharsets.UTF_8);
        String placeNameDecode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("placeName"))
                                .orElse(""), StandardCharsets.UTF_8);
        String eventNameDecode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("eventName"))
                                .orElse(""), StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?eventStartDatetime=").append(eventStartDatetimeDecode);
        stringBuilder.append("&eventEndDatetime=").append(eventEndDatetimeDecode);
        stringBuilder.append("&").append(kewWordDecode).append("=").append(searchWordDecode);
        stringBuilder.append("&").append("page").append("=").append(pageDecode);
        stringBuilder.append("&").append("placeName").append("=").append(placeNameDecode);
        stringBuilder.append("&").append("eventName").append("=").append(eventNameDecode);
        String uri = stringBuilder.toString();
        return uri;
    }

    public Map<String, Object> getEventPageInfo(Page<EventDto> findDtos) {
        List<EventResponse> toResponse = findDtos
                .stream()
                .map(EventResponse::from)
                .toList();
        Page<EventResponse> events = new PageImpl<>(
                toResponse,
                findDtos.getPageable(),
                findDtos.getTotalElements());
        int nowPage = findDtos.getPageable().getPageNumber() + 1;
        Map<String, Object> map = new HashMap<>();
        map.put("events", events);
        map.put("list", findDtos);
        map.put("nowPage", nowPage);
        map.put("startPage", Math.max(nowPage - 4, 1));
        map.put("endPage", Math.min(nowPage + 9, findDtos.getTotalPages()));
        map.put("currentPage", "/events/searchEvent");
        return map;
    }

    public Map<String, Object> getPlacePageInfo(Page<PlaceDto> findDtos) {
        List<PlaceResponse> toResponse =
                findDtos
                        .stream()
                        .map(PlaceResponse::from)
                        .toList();
        Page<PlaceResponse> places = new PageImpl<>(
                toResponse,
                findDtos.getPageable(),
                findDtos.getTotalElements());
        int nowPage = findDtos.getPageable().getPageNumber() + 1;
        Map<String, Object> map = new HashMap<>();
        map.put("places", places);
        map.put("list", findDtos);
        map.put("nowPage", nowPage);
        map.put("startPage", Math.max(nowPage - 4, 1));
        map.put("endPage", Math.min(nowPage + 9, findDtos.getTotalPages()));
        map.put("currentPage", "/places/searchPlace");
        return map;
    }
}
