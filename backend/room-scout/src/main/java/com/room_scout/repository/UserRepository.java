package com.room_scout.repository;

import com.room_scout.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { 
    boolean existsByIdentification(int identification);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
