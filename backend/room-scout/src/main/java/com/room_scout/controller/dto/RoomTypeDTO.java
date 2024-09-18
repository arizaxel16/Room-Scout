package com.room_scout.controller.dto;

public record RoomTypeDTO(
    Long id,
    String name,
    int numberOfBeds,
    int numberOfRooms,
    int guestCapacity,
    double basePrice,
    Long propertyId
) {}
