package com.email_provider.dto;

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
        String name,
        String surname,
        String propertyName,
        String roomName,
        String eventType,
        LocalDateTime eventTimestamp) {
}
