package com.room_scout.controller;

import com.room_scout.controller.dto.BookingDTO;
import com.room_scout.model.Booking;
import com.room_scout.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        Optional<BookingDTO> booking = bookingService.getBookingById(id);
        return booking.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking savedBooking = bookingService.saveBooking(bookingDTO);
        return ResponseEntity.ok(savedBooking);
    }
}
