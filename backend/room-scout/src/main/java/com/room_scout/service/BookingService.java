package com.room_scout.service;

import com.room_scout.controller.dto.BookingDTO;
import com.room_scout.controller.dto.BookingNotificationDTO;
import com.room_scout.config.RabbitMQConfig;
import com.room_scout.model.Booking;
import com.room_scout.model.RoomType;
import com.room_scout.model.Property;
import com.room_scout.repository.BookingRepository;
import com.room_scout.repository.PropertyRepository;
import com.room_scout.repository.RoomTypeRepository;
import com.room_scout.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import jakarta.annotation.Generated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final BookingEmailPublisher emailPublisher;

    public BookingDTO saveBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        RoomType roomType = roomTypeRepository.findById(bookingDTO.roomTypeId())
                .orElseThrow(() -> new IllegalArgumentException("RoomType not found with ID: " + bookingDTO.roomTypeId()));
        booking.setRoomType(roomType);
        booking.setUser(userRepository.findById(bookingDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + bookingDTO.userId())));
        booking.setStartDate(bookingDTO.startDate());
        booking.setEndDate(bookingDTO.endDate());
        booking.setTotalPrice(bookingDTO.totalPrice());
        Booking savedBooking = bookingRepository.save(booking);

        emailPublisher.sendBookingNotification(mapEntityToDTO(savedBooking), "CREATED");

        return mapEntityToDTO(savedBooking);
    }

    public Optional<BookingDTO> getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(this::mapEntityToDTO);
    }

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::mapEntityToDTO)
                .toList();
    }

    public boolean deleteBooking(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            bookingRepository.deleteById(id);

            emailPublisher.sendBookingNotification(mapEntityToDTO(booking.get()), "DELETED");

            return true;
        }
        return false;
    }
    public void deleteAllBookings() {
        bookingRepository.deleteAll();
    }

    public Optional<BookingDTO> updateBooking(Long id, BookingDTO bookingDTO) {
        return bookingRepository.findById(id)
                .map(existingBooking -> {
                    existingBooking.setStartDate(bookingDTO.startDate());
                    existingBooking.setEndDate(bookingDTO.endDate());
                    existingBooking.setTotalPrice(bookingDTO.totalPrice());
                    existingBooking.setRoomType(roomTypeRepository.findById(bookingDTO.roomTypeId())
                            .orElseThrow(() -> new IllegalArgumentException("RoomType not found")));
                    existingBooking.setUser(userRepository.findById(bookingDTO.userId())
                            .orElseThrow(() -> new IllegalArgumentException("User not found")));
                    Booking updatedBooking = bookingRepository.save(existingBooking);

                    emailPublisher.sendBookingNotification(mapEntityToDTO(updatedBooking), "UPDATED");

                    return mapEntityToDTO(updatedBooking);
                });
    }

    @Generated("excludeFromCoverage")
    private BookingDTO mapEntityToDTO(Booking booking) {
        return new BookingDTO(
                booking.getId(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getTotalPrice(),
                booking.getRoomType().getId(),
                booking.getUser().getId());
    }

    public List<long[]> checkAvailability(Long propertyId, LocalDate startDate, LocalDate endDate) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with ID: " + propertyId));
        List<RoomType> roomTypes = property.getRoomTypes();
        List<long[]> availabilityList = new ArrayList<>();
        for (RoomType roomType : roomTypes) {
            List<Booking> overlappingBookings = bookingRepository.findByRoomTypeAndDateOverlap(
                    roomType.getId(), startDate, endDate);
            int roomsBooked = overlappingBookings.size();
            int availableRooms = roomType.getNumberOfRooms() - roomsBooked;
            availabilityList.add(new long[] { roomType.getId(), availableRooms });
        }
        return availabilityList;
    }
}
