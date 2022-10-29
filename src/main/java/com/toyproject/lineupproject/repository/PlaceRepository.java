package com.toyproject.lineupproject.repository;

import com.toyproject.lineupproject.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
