package com.room_scout.controller.dto;

import java.util.List;

public record PropertyDTO(
    Long id,
    String name,
    String address,
    String country,
    String city,
    String type,
    List<RoomTypeDTO> roomTypes,
    List<AddOnDTO> addOns
) {}
