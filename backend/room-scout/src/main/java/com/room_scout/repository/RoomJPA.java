package com.room_scout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.room_scout.model.RoomORM;

public interface RoomJPA extends JpaRepository<RoomORM, Long> {
}
