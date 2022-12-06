package com.toyproject.lineupproject.service;

import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.Place;
import com.toyproject.lineupproject.domain.Post;
import com.toyproject.lineupproject.dto.PostResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.repository.AdminRepository;
import com.toyproject.lineupproject.repository.PlaceRepository;
import com.toyproject.lineupproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    private final AdminRepository adminRepository;

    private final PlaceRepository placeRepository;

    public PostResponse createPost(
            Post post,
            Admin user,
            Long placeId
    ) {
        post.addAdmin(user);
        if (placeId != null) {
            Place place = placeRepository.findById(placeId).orElseThrow(
                    () -> new GeneralException(ErrorCode.NOT_FOUND)
            );
            post.addPlace(place);
        }
        return PostResponse.of(postRepository.save(post));
    }

    public PostResponse updatePost(Long placeId, Post post) {
        Post findPost = verifiedPostById(post.getId());
        if (placeId != null) {
            Place place = placeRepository.findById(placeId).orElseThrow(
                    () -> new GeneralException(ErrorCode.NOT_FOUND)
            );
            post.setPlace(place);
        }
        findPost.updateEntity(post);
        return PostResponse.of(findPost);
    }
    public PostResponse updatePost(Post post) {
        Post findPost = verifiedPostById(post.getId());
        findPost.updateEntity(post);
        return PostResponse.of(findPost);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long postId) {
        return PostResponse.of(verifiedPostById(postId));
    }


    @Transactional(readOnly = true)
    public Page<PostResponse> getPostAllByParams(
            Map<String ,Object> param,
            Pageable pageable
    ) {

        return getPostResponses(param, pageable);
    }


    @Transactional(readOnly = true)
    public Page<PostResponse> getPostAllByParamsAndAdmin(
            String email,
            Map<String ,Object> param,
            Pageable pageable
    ) {
        Page<PostResponse> postDtos = getPostResponses(param, pageable);
        if (!postDtos.isEmpty()) {
            PostResponse response =
                    postDtos.stream()
                            .filter(p -> p.email().equals(email))
                            .findFirst().orElse(null);
            if (response == null) {
                throw new GeneralException(ErrorCode.BAD_REQUEST);
            }
        }

        return postDtos;
    }



    @Transactional(readOnly = true)
    public Page<PostResponse> getPostAllByUser(
            Admin user,
            Pageable pageable
    ) {
        Page<Post> posts =
                postRepository.findAllByAdmin(pageable, user);
        return new PageImpl<>(
                posts.getContent()
                        .stream()
                        .map(PostResponse::of)
                        .collect(Collectors.toList()),
                posts.getPageable(),
                posts.getTotalElements()
        );
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getPostAllByPlace(
            Place place,
            Pageable pageable
    ) {
        Page<Post> posts =
                postRepository.findAllByPlace(pageable, place);
        return new PageImpl<>(
                posts.getContent()
                        .stream()
                        .map(PostResponse::of)
                        .collect(Collectors.toList()),
                posts.getPageable(),
                posts.getTotalElements()
        );
    }



    private Post verifiedPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));
    }

    private Page<PostResponse> getPostResponses(Map<String, Object> param, Pageable pageable) {
        String statusKey = (String) param.get("statusKey");
        Post.Status status = null;
        if (statusKey != null) {
            status = Post.Status.valueOf(statusKey.toUpperCase());
        }
        Page<PostResponse> postDtos = postRepository.findPostPageBySearchParams(
                (String) param.get("title"),
                (String) param.get("post"),
                (String) param.get("email"),
                (String) param.get("placeName"),
                status,
                (LocalDateTime) param.get("eventStartDatetime"),
                (LocalDateTime) param.get("eventEndDatetime"),
                pageable
        );
        return postDtos;
    }

}
