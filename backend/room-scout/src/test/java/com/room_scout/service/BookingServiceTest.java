package com.room_scout.service;

import com.room_scout.controller.dto.BookingDTO;
import com.room_scout.model.Booking;
import com.room_scout.model.Property;
import com.room_scout.model.RoomType;
import com.room_scout.model.User;
import com.room_scout.repository.BookingRepository;
import com.room_scout.repository.PropertyRepository;
import com.room_scout.repository.RoomTypeRepository;
import com.room_scout.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private BookingEmailPublisher emailPublisher;

    private BookingDTO bookingDTO;
    private Booking booking;
    private RoomType roomType;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test User");

        roomType = new RoomType();
        roomType.setId(1L);
        roomType.setName("Test Room");
        roomType.setNumberOfRooms(10);

        bookingDTO = new BookingDTO(
                null,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 5),
                500.0,
                roomType.getId(),
                user.getId()
        );

        booking = new Booking();
        booking.setId(1L);
        booking.setStartDate(bookingDTO.startDate());
        booking.setEndDate(bookingDTO.endDate());
        booking.setTotalPrice(bookingDTO.totalPrice());
        booking.setRoomType(roomType);
        booking.setUser(user);
    }

    @Test
    void testSaveBooking() {
        when(roomTypeRepository.findById(bookingDTO.roomTypeId())).thenReturn(Optional.of(roomType));
        when(userRepository.findById(bookingDTO.userId())).thenReturn(Optional.of(user));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        doNothing().when(emailPublisher).sendBookingNotification(any(BookingDTO.class), anyString());

        BookingDTO savedBooking = bookingService.saveBooking(bookingDTO);

        assertNotNull(savedBooking);
        assertEquals(1L, savedBooking.id());
        assertEquals(500.0, savedBooking.totalPrice());
        verify(emailPublisher).sendBookingNotification(any(BookingDTO.class), anyString());
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void testGetBookingById() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Optional<BookingDTO> result = bookingService.getBookingById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().id());
        verify(bookingRepository).findById(1L);
    }

    @Test
    void testDeleteBooking() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        
        doNothing().when(emailPublisher).sendBookingNotification(any(BookingDTO.class), eq("DELETED"));

        boolean isDeleted = bookingService.deleteBooking(1L);

        assertTrue(isDeleted);
        verify(emailPublisher).sendBookingNotification(any(BookingDTO.class), eq("DELETED"));
        verify(bookingRepository).deleteById(1L);
    }


    @Test
    void testCheckAvailability() {
        Property property = new Property();
        property.setId(1L);
        property.setRoomTypes(List.of(roomType));

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(bookingRepository.findByRoomTypeAndDateOverlap(
                roomType.getId(),
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 5)
        )).thenReturn(List.of(booking));

        List<long[]> availability = bookingService.checkAvailability(
                1L,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 5)
        );

        assertNotNull(availability);
        assertEquals(1, availability.size());
        assertEquals(roomType.getId(), availability.get(0)[0]);
        assertEquals(9, availability.get(0)[1]); // 10 rooms - 1 booked
    }
}
