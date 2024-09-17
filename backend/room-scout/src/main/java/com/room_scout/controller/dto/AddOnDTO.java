package com.room_scout.controller.dto;

public record AddOnDTO(
    Long id,
    String name,
    double price,
    Long propertyId
) {}
