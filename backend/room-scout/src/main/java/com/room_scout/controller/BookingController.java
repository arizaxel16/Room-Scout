package com.room_scout.controller;

import com.room_scout.controller.dto.BookingDTO;
import com.room_scout.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000", "http://157.173.114.224:3000" })
@Tag(name = "Booking Management", description = "API for managing room bookings")
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Retrieve all bookings", description = "Fetch a list of all room bookings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of bookings"),
            @ApiResponse(responseCode = "204", description = "No bookings available")
    })
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        if (bookings.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Retrieve a booking by ID", description = "Fetch details of a specific booking using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved booking details"),
            @ApiResponse(responseCode = "404", description = "Booking not found with the provided ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        Optional<BookingDTO> booking = bookingService.getBookingById(id);
        return booking.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Delete a booking", description = "Remove a booking by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the booking"),
            @ApiResponse(responseCode = "404", description = "Booking not found with the provided ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        boolean isDeleted = bookingService.deleteBooking(id);
        if (!isDeleted)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create a new booking", description = "Add a new room booking to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new booking"),
            @ApiResponse(responseCode = "400", description = "Invalid input for creating a booking")
    })
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        BookingDTO savedBooking = bookingService.saveBooking(bookingDTO);
        URI location = URI.create("/bookings/" + savedBooking.id());
        return ResponseEntity.created(location).body(savedBooking);
    }

    @Operation(summary = "Update an existing booking", description = "Modify details of an existing booking by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the booking"),
            @ApiResponse(responseCode = "404", description = "Booking not found with the provided ID")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) {
        Optional<BookingDTO> updatedBooking = bookingService.updateBooking(id, bookingDTO);
        return updatedBooking.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Check room availability", description = "Check availability of rooms for booking within a specific date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved availability data"),
            @ApiResponse(responseCode = "400", description = "Invalid date format or property ID")
    })
    @GetMapping("/availability")
    public ResponseEntity<List<long[]>> checkAvailability(
            @RequestParam Long propertyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<long[]> availability = bookingService.checkAvailability(propertyId, startDate, endDate);
        return ResponseEntity.ok(availability);
    }
}
