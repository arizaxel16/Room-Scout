package com.room_scout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.room_scout.model.UserORM;

public interface UserJPA extends JpaRepository<UserORM, Long> {
}
