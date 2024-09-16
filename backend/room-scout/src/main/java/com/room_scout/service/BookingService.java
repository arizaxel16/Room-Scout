package com.room_scout.service;

import com.room_scout.controller.dto.BookingDTO;
import com.room_scout.model.Booking;
import com.room_scout.repository.BookingRepository;
import com.room_scout.repository.RoomTypeRepository;
import com.room_scout.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final UserRepository userRepository;

    public BookingDTO saveBooking(BookingDTO bookingDTO) {
        Booking booking = mapDtoToEntity(bookingDTO);
        Booking savedBooking = bookingRepository.save(booking);
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
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
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
                    bookingRepository.save(existingBooking);
                    return mapEntityToDTO(existingBooking);
                });
    }

    private BookingDTO mapEntityToDTO(Booking booking) {
        return new BookingDTO(
                booking.getId(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getTotalPrice(),
                booking.getRoomType().getId(),
                booking.getUser().getId()
        );
    }

    private Booking mapDtoToEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setStartDate(bookingDTO.startDate());
        booking.setEndDate(bookingDTO.endDate());
        booking.setTotalPrice(bookingDTO.totalPrice());
        booking.setRoomType(roomTypeRepository.findById(bookingDTO.roomTypeId())
                .orElseThrow(() -> new IllegalArgumentException("RoomType not found")));
        booking.setUser(userRepository.findById(bookingDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
        return booking;
    }
}

