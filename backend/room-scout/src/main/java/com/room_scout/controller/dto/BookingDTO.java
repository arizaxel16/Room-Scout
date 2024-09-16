package com.room_scout.controller.dto;

import java.time.LocalDate;

public record BookingDTO(
    Long id,
    LocalDate startDate,
    LocalDate endDate,
    double totalPrice,
    Long roomTypeId,
    Long userId
) {}
