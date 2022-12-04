package com.toyproject.lineupproject.service;

import com.querydsl.core.types.Predicate;
import com.toyproject.lineupproject.constant.ErrorCode;
import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.Request;
import com.toyproject.lineupproject.dto.ReqResponse;
import com.toyproject.lineupproject.exception.GeneralException;
import com.toyproject.lineupproject.repository.AdminRepository;
import com.toyproject.lineupproject.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestService {

    private final AdminRepository adminRepository;

    private final RequestRepository requestRepository;

    @Transactional(readOnly = true)
    public Page<ReqResponse> findAll(Predicate predicate, Pageable pageable) {
        try {
            Page<Request> requests = requestRepository.findAll(predicate, pageable);
            return new PageImpl<>(
                    requests.getContent()
                            .stream()
                            .map(ReqResponse::of)
                            .toList(),
                    requests.getPageable(),
                    requests.getTotalElements()
            );
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }

    }

    @Transactional(readOnly = true)
    public Page<ReqResponse> findByStatus(
            Request.Status status,
            Pageable pageable
    ) {
        try {
            Page<Request> requests =
                    requestRepository.findAllByStatus(status, pageable);

            return new PageImpl<>(
                    requests.getContent()
                            .stream()
                            .map(ReqResponse::of)
                            .toList(),
                    requests.getPageable(),
                    requests.getTotalElements()
            );
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }

    }

    @Transactional(readOnly = true)
    public Page<ReqResponse> findAllByUser(Pageable pageable, Admin user) {
        Page<Request> requests = requestRepository.findAllByAdmin(
                pageable,
                user
        );
        return new PageImpl<>(
                requests.getContent()
                        .stream()
                        .map(ReqResponse::of)
                        .toList(),
                requests.getPageable(),
                requests.getTotalElements()
        );
    }

    public ReqResponse createRequest(Request req) {
        Request save = requestRepository.save(req);
        String requestMessage = save.getRequestCode().getRequestMessage();
        return ReqResponse.of(save);
    }

    public ReqResponse findRequest(Long requestId) {
        Request request = verifiedRequestById(requestId);
        return ReqResponse.of(request);
    }

    public ReqResponse updateRequest(Long requestId, Request req) {
        Request request = verifiedRequestById(requestId);
        request.updateEntity(req);
        return ReqResponse.of(request);
    }

    private Request verifiedRequestById(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND_REQUEST));
    }
}
