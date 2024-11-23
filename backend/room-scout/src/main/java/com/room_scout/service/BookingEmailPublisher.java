package com.room_scout.service;

import com.room_scout.controller.dto.BookingNotificationDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.room_scout.config.RabbitMQConfig;
import com.room_scout.controller.dto.BookingDTO;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class BookingEmailPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;
    private final UserService userService;

    public void sendBookingNotification(BookingDTO bookingDTO, String eventType) {
        String userEmail = userService.getEmailById(bookingDTO.userId());

        BookingNotificationDTO notification = new BookingNotificationDTO(
                bookingDTO.id(),
                bookingDTO.startDate(),
                bookingDTO.endDate(),
                bookingDTO.totalPrice(),
                bookingDTO.roomTypeId(),
                bookingDTO.userId(),
                userEmail,
                eventType,
                LocalDateTime.now()
        );

        rabbitTemplate.convertAndSend(
                rabbitMQConfig.getExchange(),
                rabbitMQConfig.getRoutingKey(),
                notification
        );

        log.info("Notification sent to queue: {}", notification);
    }
}
