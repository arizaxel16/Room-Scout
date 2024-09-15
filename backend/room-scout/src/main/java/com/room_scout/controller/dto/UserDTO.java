package com.room_scout.controller.dto;

public record UserDTO(
    Long id,
    String username,
    String email,
    String name,
    String surname,
    String password,
    String role
) {}
