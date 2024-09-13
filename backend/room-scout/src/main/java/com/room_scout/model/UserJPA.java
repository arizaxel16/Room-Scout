package com.room_scout.model;

import com.room_scout.repository.UserORM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPA extends JpaRepository<UserORM, Long> {
}
