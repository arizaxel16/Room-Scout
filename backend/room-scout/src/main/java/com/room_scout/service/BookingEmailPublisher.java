package com.room_scout.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.room_scout.config.RabbitMQConfig;
import com.room_scout.controller.dto.BookingDTO;

@Service
public class BookingEmailPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;

    public BookingEmailPublisher(RabbitTemplate rabbitTemplate, RabbitMQConfig rabbitMQConfig) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
    }

    public void sendBookingEmailNotification(BookingDTO bookingDTO) {
        String message = "Booking Created/Updated/Deleted: " + bookingDTO.toString();
        rabbitTemplate.convertAndSend(rabbitMQConfig.getExchange(), rabbitMQConfig.getRoutingKey(), message);
        System.out.println("Booking email notification sent to queue: " + message);
    }
}

