package com.toyproject.lineupproject.repository;

import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
//PageRequest.of(page, size, Sort.by("requestId").descending()));
    Page<Request> findAll(Pageable pageable);

    Page<Request> findAllByAdmin(Pageable pageable, Admin admin);

}
