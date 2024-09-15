package com.room_scout.controller.dto;

public record RoomTypeDTO(
    Long id,
    String name,
    int numberOfBeds,
    int guestCapacity,
    int floorLevel,
    double basePrice,
    Long propertyId
) {}
