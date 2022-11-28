package com.toyproject.lineupproject.repository;

import com.toyproject.lineupproject.domain.Admin;
import com.toyproject.lineupproject.domain.AdminPlaceMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminPlaceMapRepository extends JpaRepository<AdminPlaceMap, Long> {
    List<AdminPlaceMap> findAllByAdmin(Admin admin);
}