package com.room_scout.controller.dto;
import java.time.LocalDate;
import java.time.LocalDateTime;
public record BookingNotificationDTO(
        Long id,
        LocalDate startDate,
        LocalDate endDate,
        double totalPrice,
        Long roomTypeId,
        Long userId,
        String email,
        String eventType,
        LocalDateTime eventTimestamp) {
}
