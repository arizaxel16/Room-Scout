package com.room_scout.model;

import com.room_scout.repository.AccommodationORM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationJPA extends JpaRepository<AccommodationORM, Long> {
}
