package com.toyproject.lineupproject.repository;

import com.toyproject.lineupproject.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String  email);
}