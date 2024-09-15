package com.room_scout.service;

import com.room_scout.controller.dto.BookingDTO;
import com.room_scout.model.Booking;
import com.room_scout.model.RoomType;
import com.room_scout.model.User;
import com.room_scout.model.AddOn;
import com.room_scout.repository.BookingRepository;
import com.room_scout.repository.RoomTypeRepository;
import com.room_scout.repository.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddOnService addOnService;

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::mapEntityToResponseDto)
                .toList();
    }

    public Optional<BookingDTO> getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(this::mapEntityToResponseDto);
    }

    public Booking saveBooking(BookingDTO bookingDTO) {
        RoomType roomType = roomTypeRepository.findById(bookingDTO.roomTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid roomTypeId"));

        User user = userRepository.findById(bookingDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid userId"));


        Booking booking = mapDtoToEntity(bookingDTO, roomType, user);
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    private Booking mapDtoToEntity(BookingDTO dto, RoomType roomType, User user) {
        Booking booking = new Booking();
        booking.setStartDate(dto.startDate());
        booking.setEndDate(dto.endDate());
        booking.setTotalPrice(dto.totalPrice());
        booking.setRoomType(roomType);
        booking.setUser(user);
        return booking;
    }

    private BookingDTO mapEntityToResponseDto(Booking booking) {
        return new BookingDTO(
                booking.getId(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getTotalPrice(),
                booking.getRoomType().getId(),
                booking.getUser().getId()
        );
    }
    public Booking updateBooking(Long id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + id));
        RoomType roomType = roomTypeRepository.findById(bookingDTO.roomTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid roomTypeId"));
        User user = userRepository.findById(bookingDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
        booking.setStartDate(bookingDTO.startDate());
        booking.setEndDate(bookingDTO.endDate());
        booking.setTotalPrice(bookingDTO.totalPrice());
        booking.setRoomType(roomType);
        booking.setUser(user);
        return bookingRepository.save(booking);
    }
}
