package com.room_scout.service;
import com.room_scout.controller.dto.BookingDTO;
import com.room_scout.model.Booking;
import com.room_scout.model.RoomType;
import com.room_scout.model.Property;
import com.room_scout.model.User;
import com.room_scout.repository.BookingRepository;
import com.room_scout.repository.PropertyRepository;
import com.room_scout.repository.RoomTypeRepository;
import com.room_scout.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingService bookingService;

    private RoomType roomType;
    private User user;
    private Booking booking;
    private Property property;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        roomType = new RoomType();
        roomType.setId(1L);
        roomType.setNumberOfRooms(10);

        user = new User();
        user.setId(1L);

        booking = new Booking();
        booking.setId(1L);
        booking.setRoomType(roomType);
        booking.setUser(user);
        booking.setStartDate(LocalDate.now());
        booking.setEndDate(LocalDate.now().plusDays(2));
        booking.setTotalPrice(200.0);

        property = new Property();
        property.setId(1L);
        property.setRoomTypes(List.of(roomType));
    }

    /*@Test
    void shouldSaveBooking() {
        BookingDTO bookingDTO = new BookingDTO(null, LocalDate.now(), LocalDate.now().plusDays(2), 200.0, roomType.getId(), user.getId());

        when(roomTypeRepository.findById(roomType.getId())).thenReturn(Optional.of(roomType));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        BookingDTO savedBooking = bookingService.saveBooking(bookingDTO);

        assertNotNull(savedBooking);
        assertEquals(200.0, savedBooking.totalPrice());
        verify(bookingRepository, times(1)).save(any(Booking.class));

    }*/

    @Test
    void shouldThrowExceptionWhenRoomTypeNotFound() {
        BookingDTO invalidBookingDTO = new BookingDTO(
                null,
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                200.0,
                9999L, // ID inexistente para RoomType
                user.getId()

        );

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.saveBooking(invalidBookingDTO);
        });
        assertEquals("RoomType not found with ID: 9999", exception.getMessage());
    }



    @Test
    void shouldGetBookingById() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Optional<BookingDTO> foundBooking = bookingService.getBookingById(1L);

        assertTrue(foundBooking.isPresent());
        assertEquals(booking.getId(), foundBooking.get().id());
    }

    @Test
    void shouldGetAllBookings() {
        when(bookingRepository.findAll()).thenReturn(List.of(booking));

        List<BookingDTO> bookings = bookingService.getAllBookings();

        assertEquals(1, bookings.size());
        assertEquals(200.0, bookings.get(0).totalPrice());
    }

        /*@Test
        void shouldDeleteBooking() {
            when(bookingRepository.existsById(1L)).thenReturn(true);

            boolean isDeleted = bookingService.deleteBooking(1L);

            assertTrue(isDeleted);
            verify(bookingRepository, times(1)).deleteById(1L);
        }

        @Test
        void shouldUpdateBooking() {
            BookingDTO updatedBookingDTO = new BookingDTO(null, LocalDate.now(), LocalDate.now().plusDays(3), 300.0, roomType.getId(), user.getId());

            when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
            when(roomTypeRepository.findById(roomType.getId())).thenReturn(Optional.of(roomType));
            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

            Optional<BookingDTO> updatedBooking = bookingService.updateBooking(1L, updatedBookingDTO);

            assertTrue(updatedBooking.isPresent());
            assertEquals(300.0, updatedBooking.get().totalPrice());
        }*/

    @Test
    void shouldCheckAvailability() {
        when(propertyRepository.findById(property.getId())).thenReturn(Optional.of(property));
        when(bookingRepository.findByRoomTypeAndDateOverlap(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(new ArrayList<>());  // No overlapping bookings

        List<long[]> availability = bookingService.checkAvailability(property.getId(), LocalDate.now(), LocalDate.now().plusDays(2));

        assertNotNull(availability);
        assertEquals(1, availability.size());
        assertEquals(roomType.getId(), availability.get(0)[0]);
        assertEquals(10, availability.get(0)[1]);  // All rooms should be available
    }

}
