package com.room_scout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.room_scout.model.AccommodationORM;

public interface AccommodationJPA extends JpaRepository<AccommodationORM, Long> {
}
