package com.toyproject.lineupproject.controller;

import com.querydsl.core.types.Predicate;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Place;
import com.toyproject.lineupproject.dto.PlaceDto;
import com.toyproject.lineupproject.dto.PlaceResponse;
import com.toyproject.lineupproject.dto.PostResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.service.PlaceService;
import com.toyproject.lineupproject.service.PostService;
import com.toyproject.lineupproject.utils.SearchUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/places")
@Controller
public class PlaceController {

    private final PlaceService placeService;

    private final SearchUtils searchUtils;

    private final PostService postService;


    @GetMapping("/searchPlace")
    public String searchPlace(
            @RequestParam HashMap<String, Object> param
    ) throws UnsupportedEncodingException {
        String uri = searchUtils.getSearchUri(param);

        return "redirect:/places" + (uri == null ? "" : uri);

    }
    @GetMapping
    public ModelAndView places(
            @QuerydslPredicate(root = Place.class) Predicate predicate,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            HttpServletRequest request
            ){

        Page<PlaceDto> findDtos = placeService.getPlacesAll(predicate, pageable);
        Map<String, Object> placePageInfo = searchUtils.getPlacePageInfo(request,findDtos);

        return new ModelAndView("place/index",placePageInfo);
    }

    @GetMapping("/{placeId}")
    public ModelAndView placeDetail(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            @PathVariable Long placeId,
            HttpServletRequest request
    ) {
        Place placeEntity = placeService.getPlaceEntity(placeId);
        PlaceResponse place =
                placeService.getPlace(placeId)
                        .map(PlaceResponse::from)
                        .orElseThrow(()-> new GeneralException(ErrorCode.NOT_FOUND));

        Page<PostResponse> findDtos = postService.getPostByPlace(placeEntity, pageable);
        Map<String, Object> map = searchUtils.getPlaceDetailPageInfo(request, findDtos);
        map.put("place", place);

        return new ModelAndView("place/detail",map);
    }


}
