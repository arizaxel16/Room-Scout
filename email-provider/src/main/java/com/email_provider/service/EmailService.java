package com.email_provider.service;

import com.email_provider.dto.BookingNotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(BookingNotificationDTO notification) {
        log.info("Preparing email for event: {}", notification.eventType());

        String subject;
        String body;

        switch (notification.eventType().toUpperCase()) {
            case "CREATED" -> {
                subject = "Booking Confirmation";
                body = buildCreateEmailContent(notification);
            }
            case "UPDATED" -> {
                subject = "Booking Updated";
                body = buildUpdateEmailContent(notification);
            }
            case "DELETED" -> {
                subject = "Booking Canceled";
                body = buildDeleteEmailContent(notification);
            }
            default -> {
                log.error("Unknown event type: {}", notification.eventType());
                return;
            }
        }

        send(notification.email(), subject, body);
    }

    private String buildCreateEmailContent(BookingNotificationDTO notification) {
        return String.format("""
            Dear %s %s,

            Your booking at %s has been confirmed!

            Details:
            - Booking ID: %d
            - Room Name: %s
            - Start Date: %s
            - End Date: %s
            - Total Price: %.2f

            Thank you for booking with us!

            Best regards,
            Your Booking Team
            """,
                notification.name(),
                notification.surname(),
                notification.propertyName(),
                notification.id(),
                notification.roomName(),
                notification.startDate(),
                notification.endDate(),
                notification.totalPrice());
    }

    private String buildUpdateEmailContent(BookingNotificationDTO notification) {
        return String.format("""
                Dear %s %s,

                Your booking at %s has been updated.

                Updated Details:
                - Booking ID: %d
                - Room Name: %s
                - Start Date: %s
                - End Date: %s
                - Total Price: %.2f

                If you have any questions, please contact support.

                Best regards,
                Your Booking Team
                """, notification.name(),
                notification.surname(),
                notification.propertyName(),
                notification.id(),
                notification.roomName(),
                notification.startDate(),
                notification.endDate(),
                notification.totalPrice());
    }

    private String buildDeleteEmailContent(BookingNotificationDTO notification) {
        return String.format("""
                Dear %s %s,

                We regret to inform you that your booking at %s has been canceled.

                Canceled Details:
                - Booking ID: %d
                - Room Name: %s
                - Start Date: %s
                - End Date: %s
                - Total Price: %.2f

                If this was a mistake, please contact support immediately.

                Best regards,
                Your Booking Team
                """,  notification.name(),
                notification.surname(),
                notification.propertyName(),
                notification.id(),
                notification.roomName(),
                notification.startDate(),
                notification.endDate(),
                notification.totalPrice());
    }

    private void send(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            log.info("Email sent successfully to {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }
}
