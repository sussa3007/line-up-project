package com.toyproject.lineupproject.repository;

import com.toyproject.lineupproject.domain.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String  email);

    Page<Admin> findAll(Pageable pageable);
}