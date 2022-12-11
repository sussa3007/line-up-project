package com.toyproject.lineupproject.controller;


import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.Post;
import com.toyproject.lineupproject.dto.PostRequest;
import com.toyproject.lineupproject.dto.PostResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.service.AdminService;
import com.toyproject.lineupproject.service.PlaceService;
import com.toyproject.lineupproject.service.PostService;
import com.toyproject.lineupproject.utils.SearchUtils;
import lombok.RequiredArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

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
        param.put("statusKey", "NOTICE");
        String uri = searchUtils.getSearchUri(param);
        return "redirect:/posts/notice" + (uri == null ? "" : uri);
    }


    @GetMapping("/searchPlaceNotice")
    public String searchPlacePosts(
            @RequestParam HashMap<String, Object> param
    ) throws UnsupportedEncodingException {
        param.put("statusKey", "PLACE_NOTICE");
        String uri = searchUtils.getSearchUri(param);

        return "redirect:/posts/place" + (uri == null ? "" : uri);
    }

    @GetMapping("/searchAdminPlaceNotice")
    public String searchAdminPlacePosts(
            @RequestParam HashMap<String, Object> param,
            Principal principal
    ) throws UnsupportedEncodingException {
        param.put("email", principal.getName());
        String uri = searchUtils.getSearchUri(param);

        return "redirect:/posts/place/admin" + (uri == null ? "" : uri);
    }

    @GetMapping("/new")
    public ModelAndView newPost(
            @RequestHeader("referer") String referer
    ) {

        return new ModelAndView(
                "post/newform",
                Map.of(
                        "postStatus", Post.Status.values(),
                        "backUrl", referer
                )
        );
    }

    @PostMapping("/new")
    public ModelAndView createPost(
            @Valid @ModelAttribute PostRequest request
    ) {

        Parser parser = Parser.builder().build();
        Node parse = parser.parse(request.post());
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String result = renderer.render(parse);

        Admin findUser = adminService.findUserByEmail(request.email());
        Post post = request.dtoToPost(result);
        PostResponse response = postService.createPost(post, findUser, 1L);
        return new ModelAndView(
                "alert",
                Map.of(
                        "msg", "정상적으로 작성 되었습니다.",
                        "nextPage", "/posts/" + response.id()
                )
        );
    }

    @GetMapping("/{postId}")
    public ModelAndView getPost(
            @PathVariable("postId") Long postId
    ) {
        PostResponse post = postService.getPost(postId);
        String backUrl = "";
        if (post.status().equals(Post.Status.NOTICE)) {
            backUrl = "/searchNotice";
        } else if (post.status().equals(Post.Status.PLACE_NOTICE)) {
            backUrl = "/searchPlaceNotice";
        }
        return new ModelAndView(
                "post/post",
                Map.of(
                        "post", post,
                        "backUrl", "/posts" + backUrl
                )
        );

    }

    @GetMapping("/{placeId}/new")
    public ModelAndView newPlacePost(
            @RequestHeader("referer") String referer,
            @PathVariable("placeId") Long placeId
    ) {

        return new ModelAndView(
                "post/placepostnewform",
                Map.of(
                        "placeId", placeId,
                        "postStatus", Post.Status.values(),
                        "backUrl", referer
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

    @GetMapping("/{postId}/modify")
    public ModelAndView modifyPost(
            @PathVariable("postId") Long postId,
            Principal principal
    ) {
        PostResponse post = postService.getPost(postId);
        if (principal.getName().equals(post.email()) || principal.getName().equals(adminEmail)) {

        } else {
            throw new GeneralException(ErrorCode.ACCESS_DENIED);
        }
        String backUrl = "";
        if (post.status().equals(Post.Status.NOTICE)) {
            backUrl = "/searchNotice";
        } else if (post.status().equals(Post.Status.PLACE_NOTICE)) {
            backUrl = "/searchAdminPlaceNotice";
        }
        return new ModelAndView(
                "post/modifyform",
                Map.of(
                        "post", post,
                        "backUrl", "/posts" + backUrl
                )
        );

    }

    @PostMapping("/{postId}/modify")
    public ModelAndView updatePost(
            @Valid @ModelAttribute PostRequest request,
            @PathVariable("postId") Long postId,
            Principal principal
    ) {

        Parser parser = Parser.builder().build();
        Node parse = parser.parse(request.post());
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String result = renderer.render(parse);

        Post post = request.dtoToPost(result);
        post.setId(postId);
        if (principal.getName().equals(request.email()) || principal.getName().equals(adminEmail)) {
            PostResponse response = postService.updatePost(post);
        } else {
            throw new GeneralException(ErrorCode.POST_ACCESS_DENIED);
        }


        return new ModelAndView(
                "alert",
                Map.of(
                        "msg", "정상적으로 수정 되었습니다.",
                        "nextPage", "/posts/" + postId
                )
        );
    }

    @GetMapping("/place/admin")
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

        return new ModelAndView("post/placeindex", adminPostPageInfo);
    }

    @GetMapping("/place")
    public ModelAndView getPostAllByPlace(
            @RequestParam HashMap<String, Object> param,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            HttpServletRequest request
    ) {
        Page<PostResponse> findDtos =
                postService.getPostAllByParamsAndPlace(param, pageable);
        Map<String, Object> searchPlaceNoticePageInfo =
                searchUtils.getSearchPlaceNoticePageInfo(request, findDtos);
        return new ModelAndView("post/placeindex", searchPlaceNoticePageInfo);
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
        return new ModelAndView("post/index", searchPostPageInfo);

    }

    @GetMapping("/{postId}/delete/check")
    public ModelAndView deletePostCheck(
            @PathVariable("postId") Long postId,
            @RequestHeader("referer") String referer
    ) {
        return new ModelAndView(
                "confirm",
                Map.of(
                        "msg", "공지사항을 삭제 하시겠습니까?" ,
                        "nextPage", "/posts/"+postId+"/delete",
                        "prevPage", referer
                )
        );
    }

    @GetMapping("/{postId}/delete")
    public ModelAndView deletePost(
            @PathVariable("postId") Long postId,
            Principal principal
    ) {
        PostResponse post = postService.getPost(postId);
        if (principal.getName().equals(post.email()) || principal.getName().equals(adminEmail)) {
            postService.deleteById(postId);
        } else {
            throw new GeneralException(ErrorCode.POST_ACCESS_DENIED);
        }

        return new ModelAndView(
                "alert",
                Map.of(
                        "msg", "정상적으로 삭제 되었습니다."+'\n'+"공지사항 페이지로 돌아갑니다.",
                        "nextPage", "/posts/searchNotice"
                )
        );
    }
}
