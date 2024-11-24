package com.room_scout.service;

import com.room_scout.controller.dto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.room_scout.config.RabbitMQConfig;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BookingEmailPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;
    private final UserService userService;
    private final PropertyService propertyService;
    private final RoomTypeService roomTypeService;

    public void sendBookingNotification(BookingDTO bookingDTO, String eventType) {
        String userEmail = userService.getEmailById(bookingDTO.userId());

        Optional<UserDTO> user = userService.getUserById(bookingDTO.userId());
        Optional<RoomTypeDTO> room = roomTypeService.getRoomTypeById(bookingDTO.roomTypeId());
        Long propertyId = room.map(RoomTypeDTO::propertyId).orElse(0L);
        Optional<PropertyDTO> property = Optional.ofNullable(propertyService.getPropertyById(propertyId));

        String propertyName = property.map(PropertyDTO::name).orElse("Unknown Property");
        String userName = user.map(UserDTO::name).orElse("Unknown User");
        String userLastName = user.map(UserDTO::surname).orElse("Unknown User");
        String roomName = room.map(RoomTypeDTO::name).orElse("Unknown Room");

        BookingNotificationDTO notification = new BookingNotificationDTO(
                bookingDTO.id(),
                bookingDTO.startDate(),
                bookingDTO.endDate(),
                bookingDTO.totalPrice(),
                bookingDTO.roomTypeId(),
                bookingDTO.userId(),
                userEmail,
                userName,
                userLastName,
                propertyName,
                roomName,
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
