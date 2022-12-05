package com.toyproject.lineupproject.controller;

import com.querydsl.core.types.Predicate;
import com.toyproject.lineupproject.auth.jwt.utils.JwtAuthorityUtils;
import com.toyproject.lineupproject.constant.AdminOperationStatus;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.constant.EventStatus;
import com.toyproject.lineupproject.constant.PlaceType;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.Place;
import com.toyproject.lineupproject.dto.*;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.service.AdminService;
import com.toyproject.lineupproject.service.EventService;
import com.toyproject.lineupproject.service.PlaceService;
import com.toyproject.lineupproject.utils.SearchUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Validated
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final EventService eventService;
    private final PlaceService placeService;

    private final AdminService adminService;

    private final SearchUtils searchUtils;


    @GetMapping("/searchPlace")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    public String searchSuperAdminPlace(
            @RequestParam HashMap<String, Object> param
    ) throws UnsupportedEncodingException {
        String uri = searchUtils.getSearchUri(param);

        return "redirect:/admin/places-all" + (uri == null ? "" : uri);

    }

    @GetMapping("/searchEvent")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    public String searchSuperAdminEvents(
            @RequestParam HashMap<String, Object> param
    ) throws UnsupportedEncodingException {
        String uri = searchUtils.getSearchUri(param);

        return "redirect:/admin/events-all" + (uri == null ? "" : uri);

    }

    @GetMapping("/searchUser")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    public String searchSuperAdminUsers(
            @RequestParam HashMap<String, Object> param
    ) throws UnsupportedEncodingException {
        String uri = searchUtils.getSearchUri(param);

        return "redirect:/admin/user-all" + (uri == null ? "" : uri);

    }

    @GetMapping("/searchAdminPlace")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPERADMIN')")
    public String searchAdminPlace(
            @RequestParam HashMap<String, Object> param,
            Principal principal
    ) throws UnsupportedEncodingException {
        param.put("email", principal.getName());
        String uri = searchUtils.getSearchUri(param);

        return "redirect:/admin/places" + (uri == null ? "" : uri);

    }

    @GetMapping("/searchAdminEvent")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPERADMIN')")
    public String searchAdminEvent(
            @RequestParam HashMap<String, Object> param,
            Principal principal
    ) throws UnsupportedEncodingException {
        param.put("email", principal.getName());
        String uri = searchUtils.getSearchUri(param);

        return "redirect:/admin/events" + (uri == null ? "" : uri);

    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @GetMapping("/user-all")
    public ModelAndView superAdminUsers(
            @RequestParam HashMap<String, Object> param,
            @QuerydslPredicate(root = Admin.class) Predicate predicate,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            HttpServletRequest request

    ) {
        Page<AdminResponse> allUser = adminService.finderUsers(
                (String) param.get("statusKey"),
                predicate,
                pageable
        );
        Map<String, Object> searchUserPageInfo = searchUtils.getSearchUserPageInfo(request, allUser);
        return new ModelAndView(
                "admin/users",
                searchUserPageInfo
        );
    }

    @GetMapping("/places")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPERADMIN')")
    public ModelAndView adminPlaces(
            @QuerydslPredicate(root = Place.class) Predicate predicate,
            Principal principal,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<PlaceDto> findDtos = placeService.getPlacesByAdmin(principal.getName(), pageable, predicate);
        Map<String, Object> placePageInfo = searchUtils.getAdminPlacePageInfo(request, findDtos);
        placePageInfo.put("placeTypeOption", PlaceType.values());

        return new ModelAndView(
                "admin/places",
                placePageInfo
        );
    }

    @GetMapping("/places-all")
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    public ModelAndView superAdminPlaces(
            @QuerydslPredicate(root = Place.class) Predicate predicate,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<PlaceDto> findDtos = placeService.getPlacesAll(predicate, pageable);
        Map<String, Object> placePageInfo = searchUtils.getSuperAdminPlacePageInfo(request, findDtos);
        placePageInfo.put("placeTypeOption", PlaceType.values());


        return new ModelAndView(
                "admin/place-all",
                placePageInfo
        );
    }


    @GetMapping("/places/{placeId}")
    public ModelAndView adminPlaceDetail(
            @PathVariable Long placeId,
            @PageableDefault Pageable pageable,
            HttpServletRequest request,
            Authentication authentication
    ) {
        PlaceResponse place = placeService.getPlace(placeId)
                .map(PlaceResponse::from)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));
        Page<EventDto> findDtos = eventService.getEvent(placeId, pageable);
        Map<String, Object> map = searchUtils.getEventPageInfo(request, findDtos);
        map.put("adminOperationStatus", AdminOperationStatus.MODIFY);
        map.put("place", place);
        map.put("placeTypeOption", PlaceType.values());
        if (authentication.getAuthorities().equals(JwtAuthorityUtils.SUPER_ADMIN_ROLES)) {
            map.put("backUrl", "/admin/places-all");
        } else {
            map.put("backUrl", "/admin/searchAdminPlace");
        }
        return new ModelAndView(
                "admin/place-detail",
                map

        );
    }

    @GetMapping("/places/new")
    public String newPlace(
            Model model,
            Authentication authentication
    ) {
        model.addAttribute("adminOperationStatus", AdminOperationStatus.CREATE);
        model.addAttribute("placeTypeOption", PlaceType.values());
        if (authentication.getAuthorities().equals(JwtAuthorityUtils.SUPER_ADMIN_ROLES)) {
            model.addAttribute("backUrl", "/admin/places-all");
        } else {
            model.addAttribute("backUrl", "/admin/searchAdminPlace");
        }
        return "admin/place-detail";
    }

    @ResponseStatus(HttpStatus.SEE_OTHER)
    @PostMapping("/places")
    public String upsertPlace(
            @Valid PlaceRequest placeRequest,
            RedirectAttributes redirectAttributes,
            Authentication authentication
    ) {
        AdminOperationStatus status = placeRequest.id() != null ? AdminOperationStatus.MODIFY : AdminOperationStatus.CREATE;
        placeService.upsertPlace(placeRequest.toDto());

        redirectAttributes.addFlashAttribute("adminOperationStatus", status);
        if (authentication.getAuthorities().equals(JwtAuthorityUtils.SUPER_ADMIN_ROLES)) {
            redirectAttributes.addFlashAttribute("redirectUrl", "/admin/places-all");
        } else {
            redirectAttributes.addFlashAttribute("redirectUrl", "/admin/searchAdminPlace");
        }

        return "redirect:/admin/confirm";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPERADMIN')")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    @GetMapping("/places/{placeId}/delete")
    public String deletePlace(
            @PathVariable Long placeId,
            RedirectAttributes redirectAttributes,
            Authentication authentication
    ) {
        placeService.removePlace(placeId);

        redirectAttributes.addFlashAttribute("adminOperationStatus", AdminOperationStatus.DELETE);
        if (authentication.getAuthorities().equals(JwtAuthorityUtils.SUPER_ADMIN_ROLES)) {
            redirectAttributes.addFlashAttribute("redirectUrl", "/admin/places-all");
        } else {
            redirectAttributes.addFlashAttribute("redirectUrl", "/admin/searchAdminPlace");
        }

        return "redirect:/admin/confirm";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPERADMIN')")
    @GetMapping("/places/{placeId}/newEvent")
    public String newEvent(@PathVariable Long placeId, Model model) {
        EventResponse event = placeService.getPlace(placeId)
                .map(EventResponse::empty)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));

        model.addAttribute("adminOperationStatus", AdminOperationStatus.CREATE);
        model.addAttribute("event'StatusOption", EventStatus.values());
        model.addAttribute("event", event);

        return "admin/event-detail";
    }

    @ResponseStatus(HttpStatus.SEE_OTHER)
    @PostMapping("/places/{placeId}/events")
    public String upsertEvent(
            @Valid EventRequest eventRequest,
            @PathVariable Long placeId,
            RedirectAttributes redirectAttributes
    ) {
        AdminOperationStatus status = eventRequest.id() != null ? AdminOperationStatus.MODIFY : AdminOperationStatus.CREATE;
        eventService.upsertEvent(eventRequest.toDto(PlaceDto.idOnly(placeId)));

        redirectAttributes.addFlashAttribute("adminOperationStatus", status);
        redirectAttributes.addFlashAttribute("redirectUrl", "/admin/places/" + placeId);

        return "redirect:/admin/confirm";
    }

    @ResponseStatus(HttpStatus.SEE_OTHER)
    @GetMapping("/events/{eventId}/delete")
    public String deleteEvent(
            @PathVariable Long eventId,
            RedirectAttributes redirectAttributes
    ) {
        eventService.removeEvent(eventId);

        redirectAttributes.addFlashAttribute("adminOperationStatus", AdminOperationStatus.DELETE);
        redirectAttributes.addFlashAttribute("redirectUrl", "/admin/events");

        return "redirect:/admin/confirm";
    }

    @GetMapping("/events")
    public ModelAndView adminEvents(
            @RequestParam HashMap<String, Object> param,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<EventDto> findDto;

        findDto = eventService.getEventsParams(param, pageable);
        Map<String, Object> adminEventPageInfo =
                searchUtils.getAdminEventPageInfo(request, findDto);
        adminEventPageInfo.put("eventStatusOption", EventStatus.values());

        return new ModelAndView(
                "admin/events",
                adminEventPageInfo
        );
    }

    /* todo 페이지 구현 예정*/
    @GetMapping("/events-all")
    public ModelAndView superAdminEvents(
            @RequestParam HashMap<String, Object> param,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            HttpServletRequest request
    ) {
        String statusKey = (String) param.get("statusKey");
        Page<EventDto> findDtos = eventService.getEventsParams(param, pageable);
        Map<String, Object> eventPageInfo =
                searchUtils.getSuperAdminEventPageInfo(
                        request, findDtos);
        eventPageInfo.put("placeTypeOption", PlaceType.values());

        return new ModelAndView(
                "admin/events",
                eventPageInfo
        );
    }

    @GetMapping("/events/{eventId}")
    public ModelAndView adminEventDetail(@PathVariable Long eventId) {
        EventResponse event = eventService.getEvent(eventId)
                .map(EventResponse::from)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));

        return new ModelAndView(
                "admin/event-detail",
                Map.of(
                        "adminOperationStatus", AdminOperationStatus.MODIFY,
                        "event", event,
                        "eventStatusOption", EventStatus.values()
                )
        );
    }

    @GetMapping("/confirm")
    public String confirm(Model model) {
        if (!model.containsAttribute("redirectUrl")) {
            throw new GeneralException(ErrorCode.BAD_REQUEST);
        }

        return "admin/confirm";
    }

    @GetMapping("/user/{userId}")
    public ModelAndView userInfoBySuperAdmin(
            @PathVariable Long userId,
            @RequestHeader("referer") String referer
    ) {
        Admin user = adminService.findUserById(userId);

        return new ModelAndView(
                "auth/modify",
                Map.of(
                        "adminOperationStatus", AdminOperationStatus.MODIFY,
                        "user", user,
                        "backUrl", referer
                )
        );
    }

    @PostMapping("/modify")
    public ModelAndView updateUserBySuperAdmin(
            @Valid @ModelAttribute AdminRequest adminRequest
    ) {
        adminService.updateUser(adminRequest.dtoToAdminFull());

        return new ModelAndView(
                "/alert",
                Map.of(
                        "msg", "정상적으로 수정 되었습니다.",
                        "nextPage", "/admin/user-all"
                )
        );
    }

}