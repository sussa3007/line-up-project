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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

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
                throw new GeneralException(ErrorCode.POST_ACCESS_DENIED);
            }
        }

        return postDtos;
    }
    @Transactional(readOnly = true)
    public Page<PostResponse> getPostAllByParamsAndPlace(
            Map<String ,Object> param,
            Pageable pageable
    ) {
        Page<PostResponse> postDtos = getPostResponses(param, pageable);

        return postDtos;
    }


    public void deleteById(Long postId) {
        Post post = verifiedPostById(postId);
        postRepository.delete(post);
    }





/* 검증 / 유틸 로직*/


    public Post verifiedPostById(Long postId) {
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
