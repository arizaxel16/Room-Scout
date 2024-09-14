package com.room_scout.model;

import com.room_scout.repository.RoomORM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomJPA extends JpaRepository<RoomORM, Long> {
}
