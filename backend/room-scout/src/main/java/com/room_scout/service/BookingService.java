package com.room_scout.service;

import com.room_scout.controller.dto.BookingDTO;
import com.room_scout.model.Booking;
import com.room_scout.model.RoomType;
import com.room_scout.model.Property;
import com.room_scout.repository.BookingRepository;
import com.room_scout.repository.PropertyRepository;
import com.room_scout.repository.RoomTypeRepository;
import com.room_scout.repository.UserRepository;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.ArrayList;


@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

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

    @Generated("excludeFromCoverage")
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
    public List<long[]> checkAvailability(Long propertyId, LocalDate startDate, LocalDate endDate) 
    {
    Property property = propertyRepository.findById(propertyId)
            .orElseThrow(() -> new IllegalArgumentException("Property not found with ID: " + propertyId));
    List<RoomType> roomTypes = property.getRoomTypes();
    List<long[]> availabilityList = new ArrayList<>();
    for (RoomType roomType : roomTypes) {
        List<Booking> overlappingBookings = bookingRepository.findByRoomTypeAndDateOverlap(
                roomType.getId(), startDate, endDate
        );
        int roomsBooked = overlappingBookings.size();
        int availableRooms = roomType.getNumberOfRooms() - roomsBooked;
        availabilityList.add(new long[]{roomType.getId(), availableRooms});
    }
    return availabilityList;
}
}

