package com.email_provider.service;

import com.email_provider.dto.BookingNotificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    public void sendEmail(BookingNotificationDTO notification) {
        log.info("Sending EMAIL (FUNCIONOOOOO)");
    }
}
