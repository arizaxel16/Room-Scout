package com.email_provider.listener;

import com.email_provider.dto.BookingNotificationDTO;
import com.email_provider.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailEventListener {

    private final EmailService emailService;


    @RabbitListener(queues = "emailQueue")
    public void handleEmailNotification(BookingNotificationDTO notification) {
        log.info("Received notification: {}", notification);

        emailService.sendEmail(notification);
    }
}
