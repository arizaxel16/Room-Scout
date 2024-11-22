package com.room_scout.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.room_scout.controller.dto.BookingDTO;

@Service
public class BookingEmailPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingKey;

    public BookingEmailPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendBookingEmailNotification(BookingDTO bookingDTO) {
        String message = "Booking Created/Updated/Deleted: " + bookingDTO.toString();

        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("Booking email notification sent to queue: " + message);
    }
}
