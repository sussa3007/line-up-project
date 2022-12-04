package com.toyproject.lineupproject.utils;

import com.toyproject.lineupproject.constant.AdminOperationStatus;
import com.toyproject.lineupproject.domain.Request;
import com.toyproject.lineupproject.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class SearchUtils {

    public String getSearchUri(HashMap<String, Object> param) {
        String eventStartDatetimeEncode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("eventStartDatetime"))
                                .orElse(""), StandardCharsets.UTF_8);
        String eventEndDatetimeEncode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("eventEndDatetime"))
                                .orElse(""), StandardCharsets.UTF_8);
        String kewWordEncode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("kewWord"))
                                .orElse(""), StandardCharsets.UTF_8);
        String searchWordEncode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("searchWord"))
                                .orElse(""), StandardCharsets.UTF_8);
        String pageEncode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("page"))
                                .orElse(""), StandardCharsets.UTF_8);
        String placeNameEncode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("placeName"))
                                .orElse(""), StandardCharsets.UTF_8);
        String eventNameEncode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("eventName"))
                                .orElse(""), StandardCharsets.UTF_8);
        String phoneNumberEncode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("phoneNumber"))
                                .orElse(""), StandardCharsets.UTF_8);
        String addressEncode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("address"))
                                .orElse(""), StandardCharsets.UTF_8);
        String emailEncode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("email"))
                                .orElse(""), StandardCharsets.UTF_8);
        String statusKeyEncode =
                URLEncoder.encode(
                        Optional.ofNullable((String) param.get("statusKey"))
                                .orElse(""), StandardCharsets.UTF_8);


        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("?");
        if (!eventStartDatetimeEncode.isEmpty())
            stringBuilder.append("eventStartDatetime=").append(eventStartDatetimeEncode);

        if (!eventEndDatetimeEncode.isEmpty())
            stringBuilder.append("&eventEndDatetime=").append(eventEndDatetimeEncode);

        if (!searchWordEncode.isEmpty())
            stringBuilder.append("&").append(kewWordEncode).append("=").append(searchWordEncode);

        if (!placeNameEncode.isEmpty())
            stringBuilder.append("&").append("placeName").append("=").append(placeNameEncode);

        if (!eventNameEncode.isEmpty())
            stringBuilder.append("&").append("eventName").append("=").append(eventNameEncode);

        if (!phoneNumberEncode.isEmpty())
            stringBuilder.append("&").append("phoneNumber").append("=").append(phoneNumberEncode);

        if (!addressEncode.isEmpty())
            stringBuilder.append("&").append("address").append("=").append(addressEncode);

        if (!emailEncode.isEmpty())
            stringBuilder.append("&").append("email").append("=").append(emailEncode.replace("%40", "@"));

        if (!statusKeyEncode.isEmpty())
            stringBuilder.append("&").append("statusKey").append("=").append(statusKeyEncode);

        if (!pageEncode.isEmpty())
            stringBuilder.append("&").append("page").append("=").append(pageEncode);

        String uri = stringBuilder.toString();
        return uri;
    }
    public Map<String, Object> getRequestPageInfo(
            HttpServletRequest request,
            Page<ReqResponse> findDtos,
            AdminResponse user
    ) {
        String forPage = getRequestUriParam(request);
        Map<String, Object> map = getRequestPageMap(forPage, findDtos);
        map.put("adminOperationStatus", AdminOperationStatus.MODIFY);
        map.put("user", user);
        map.put("backUrl", "/events");
        return map;
    }

    public Map<String, Object> getSearchUserPageInfo(
            HttpServletRequest request,
            Page<AdminResponse> findDtos
    ) {
        String forPage = getRequestUriParam(request);
        Map<String, Object> map = getUserPageMap(forPage, findDtos);
        map.put("currentPage", "/admin/searchUser");
        return map;
    }

    public Map<String, Object> getEventPageInfo(
            HttpServletRequest request,
            Page<EventDto> findDtos
    ) {
        String forPage = getRequestUriParam(request);
        Map<String, Object> map = getEventPageMap(forPage, findDtos);
        map.put("currentPage", "/events/searchEvent");
        return map;
    }

    public Map<String, Object> getAdminEventPageInfo(
            HttpServletRequest request,
            Page<EventDto> findDtos) {
        String forPage = getRequestUriParam(request);
        Map<String, Object> map = getEventPageMap(forPage, findDtos);
        map.put("currentPage", "/admin/searchAdminEvent");
        return map;
    }

    public Map<String, Object> getSuperAdminEventPageInfo(
            HttpServletRequest request,
            Page<EventDto> findDtos
    ) {
        String forPage = getRequestUriParam(request);
        Map<String, Object> map = getEventPageMap(forPage, findDtos);
        map.put("currentPage", "/admin/searchEvent");
        return map;
    }

    public Map<String, Object> getPlacePageInfo(
            HttpServletRequest request,
            Page<PlaceDto> findDtos
    ) {
        String forPage = getRequestUriParam(request);
        Map<String, Object> map = getPlacePageMap(forPage, findDtos);
        map.put("currentPage", "/places/searchPlace");
        return map;
    }

    public Map<String, Object> getAdminPlacePageInfo(
            HttpServletRequest request,
            Page<PlaceDto> findDtos) {
        String forPage = getRequestUriParam(request);
        Map<String, Object> map = getPlacePageMap(forPage, findDtos);
        map.put("currentPage", "/admin/searchAdminPlace");
        return map;
    }

    public Map<String, Object> getSuperAdminPlacePageInfo(
            HttpServletRequest request,
            Page<PlaceDto> findDtos
    ) {
        String forPage = getRequestUriParam(request);
        Map<String, Object> map = getPlacePageMap(forPage, findDtos);
        map.put("currentPage", "/admin/searchPlace");

        return map;
    }
    public Map<String, Object> getSuperAdminRequestPageInfo(
            HttpServletRequest request,
            Page<ReqResponse> findDtos
    ) {
        String forPage = getRequestUriParam(request);
        Map<String, Object> map = getRequestPageMap(forPage, findDtos);
        map.put("currentPage", "/requests/searchRequest");

        return map;
    }


    private static Map<String, Object> getUserPageMap(
            String forPage,
            Page<AdminResponse> findDtos) {
        List<AdminResponse> toResponse =
                findDtos.getContent();
        Page<AdminResponse> allUser = new PageImpl<>(
                toResponse,
                findDtos.getPageable(),
                findDtos.getTotalElements());
        Map<String, Object> map = getPageMap(forPage, findDtos);

        map.put("allUser", allUser);
        return map;
    }

    private static Map<String, Object> getPlacePageMap(
            String forPage,
            Page<PlaceDto> findDtos) {
        List<PlaceResponse> toResponse =
                findDtos
                        .stream()
                        .map(PlaceResponse::from)
                        .toList();
        Page<PlaceResponse> places = new PageImpl<>(
                toResponse,
                findDtos.getPageable(),
                findDtos.getTotalElements());
        Map<String, Object> map = getPageMap(forPage, findDtos);

        map.put("places", places);
        return map;
    }

    private static Map<String, Object> getEventPageMap(
            String forPage,
            Page<EventDto> findDtos
    ) {
        List<EventResponse> toResponse = findDtos
                .stream()
                .map(EventResponse::from)
                .toList();
        Page<EventResponse> events = new PageImpl<>(
                toResponse,
                findDtos.getPageable(),
                findDtos.getTotalElements());

        Map<String, Object> map = getPageMap(forPage, findDtos);

        map.put("events", events);

        return map;
    }



    private static Map<String, Object> getRequestPageMap(
            String forPage,
            Page<ReqResponse> findDtos
    ) {
        List<ReqResponse> toResponse = findDtos.getContent();

        Map<String, Object> map = getPageMap(forPage, findDtos);

        map.put("requests", findDtos);
        map.put("requestStatus", Request.Status.values());
        return map;
    }

    private static Map<String, Object> getPageMap(String forPage, Page findDtos) {
        int nowPage = findDtos.getPageable().getPageNumber() + 1;
        Map<String, Object> map = new HashMap<>();
        int startPage = nowPage-1;
        int endPage = nowPage+1;
        int totalPages = findDtos.getTotalPages();

        if (nowPage == totalPages || totalPages == 0) {
            map.put("endPage", 0);
        } else {
            map.put("endPage", endPage);
        }
        map.put("startPage", startPage);
        map.put("nowPage", nowPage);
        map.put("totalPage", totalPages);
        map.put("list", findDtos);
        map.put("forPage", forPage);
        return map;
    }


    public String getRequestUriParam(HttpServletRequest request) {
        String queryString = request.getQueryString();
        String requestUri = request.getRequestURI();
        if (queryString == null) {
            return requestUri;
        } else {
            if (queryString.contains("&page")) {
                return requestUri + "?" + queryString
                        .split("&page")[0];
            } else {
                return requestUri + "?" + queryString
                        .split("page")[0];
            }
        }

    }
}
