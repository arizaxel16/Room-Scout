package com.room_scout.controller.dto;

public record PropertyDTO(
    Long id,
    String name,
    String address,
    String country,
    String city,
    String type
) {}
