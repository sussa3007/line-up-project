package com.toyproject.lineupproject.controller;


import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.Post;
import com.toyproject.lineupproject.dto.PostRequest;
import com.toyproject.lineupproject.dto.PostResponse;
import com.toyproject.lineupproject.service.AdminService;
import com.toyproject.lineupproject.service.PlaceService;
import com.toyproject.lineupproject.service.PostService;
import com.toyproject.lineupproject.utils.SearchUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Validated
@RequestMapping("/posts")
@Controller
public class PostController {
    private final AdminService adminService;

    private final SearchUtils searchUtils;

    private final PostService postService;

    private final PlaceService placeService;

    @GetMapping("/searchPost")
    public String searchPosts(
            @RequestParam Map<String, Object> param
    ) {
        String uri = searchUtils.getSearchUri(param);
        return "redirect:/posts" + (uri == null ? "" : uri);
    }
    @GetMapping("/searchReview")
    public String searchReview(
            @RequestParam Map<String, Object> param
    ) {
        String uri = searchUtils.getSearchUri(param);
        return "redirect:/posts/review" + (uri == null ? "" : uri);
    }
    @GetMapping("/searchNotice")
    public String searchNotice(
            @RequestParam Map<String, Object> param
    ) {
        String uri = searchUtils.getSearchUri(param);
        return "redirect:/posts/notice" + (uri == null ? "" : uri);
    }


    @GetMapping("/searchAdminPost")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPERADMIN')")
    public String searchAdminPosts(
            @RequestParam HashMap<String, Object> param,
            Principal principal
    ) throws UnsupportedEncodingException {
        param.put("email", principal.getName());
        String uri = searchUtils.getSearchUri(param);

        return "redirect:/posts/user" + (uri == null ? "" : uri);

    }

    @GetMapping("/new")
    public ModelAndView newPost(
    ) {
        return new ModelAndView(
                "post/newform",
                Map.of(
                        "postStatus", Post.Status.values()
                )
        );
    }

    @PostMapping("/new")
    public ModelAndView createPost(
            @Valid @ModelAttribute PostRequest request
    ) {
        Admin findUser = adminService.findUserByEmail(request.email());
        Post post = request.dtoToPost();
        PostResponse response = postService.createPost(post, findUser, null);
        return new ModelAndView(
                "alert",
                Map.of(
                        "msg", "정상적으로 작성 되었습니다.",
                        "nextPage", "/posts/" + response.id()
                )
        );
    }

    @PostMapping("/{placeId}/new")
    public ModelAndView createPostAndPlace(
            @PathVariable("placeId") Long placeId,
            @Valid @ModelAttribute PostRequest request
    ) {
        Admin findUser = adminService.findUserByEmail(request.email());
        Post post = request.dtoToPost();
        PostResponse response = postService.createPost(post, findUser, placeId);
        return new ModelAndView(
                "alert",
                Map.of(
                        "msg", "정상적으로 작성 되었습니다.",
                        "nextPage", "/posts/" + response.id()
                )
        );
    }

    @GetMapping("/user")
    public ModelAndView getPostByAdmin(
            @RequestParam HashMap<String, Object> param,
            Principal principal,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<PostResponse> findDtos =
                postService.getPostAllByParamsAndAdmin(principal.getName(), param, pageable);
        Map<String, Object> adminPostPageInfo =
                searchUtils.getUserPostPageInfo(request, findDtos);

        return new ModelAndView("post/index", adminPostPageInfo);
    }

    @GetMapping("/notice")
    public ModelAndView getPostsNotice(
            @RequestParam HashMap<String, Object> param,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<PostResponse> findDtos = postService.getPostAllByParams(param, pageable);
        Map<String, Object> searchPostPageInfo =
                searchUtils.getSearchNoticePageInfo(request, findDtos);
        searchPostPageInfo.put("status", "NOTICE");
        return new ModelAndView("post/index", searchPostPageInfo);

    }
}
