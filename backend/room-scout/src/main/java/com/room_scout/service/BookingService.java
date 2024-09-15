package com.room_scout.service;

import com.room_scout.controller.dto.BookingDTO;
import com.room_scout.model.Booking;
import com.room_scout.model.RoomType;
import com.room_scout.model.User;
import com.room_scout.model.AddOn;
import com.room_scout.repository.BookingRepository;
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
    private RoomTypeService roomTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddOnService addOnService;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking saveBooking(BookingDTO bookingDTO) {
        Optional<RoomType> roomType = roomTypeService.getRoomTypeById(bookingDTO.roomTypeId());
        Optional<User> user = userService.getUserById(bookingDTO.userId());
        List<AddOn> addOns = addOnService.getAddOnsByIds(bookingDTO.addOnIds());

        if (roomType.isEmpty() || user.isEmpty()) {
            throw new IllegalArgumentException("Invalid roomTypeId or userId");
        }

        Booking booking = mapDtoToEntity(bookingDTO, roomType.get(), user.get(), addOns);
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    private Booking mapDtoToEntity(BookingDTO dto, RoomType roomType, User user, List<AddOn> addOns) {
        Booking booking = new Booking();
        booking.setStartDate(dto.startDate());
        booking.setEndDate(dto.endDate());
        booking.setTotalPrice(dto.totalPrice());
        booking.setRoomType(roomType);
        booking.setUser(user);
        booking.setAddOns(addOns);
        return booking;
    }
}
