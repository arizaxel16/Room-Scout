package com.room_scout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.room_scout.controller.dto.BookingDTO;
import com.room_scout.model.Property;
import com.room_scout.model.RoomType;
import com.room_scout.model.User;
import com.room_scout.repository.PropertyRepository;
import com.room_scout.repository.RoomTypeRepository;
import com.room_scout.repository.UserRepository;
import com.room_scout.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private UserRepository userRepository;

    private Long propertyId;
    private Long roomTypeId;
    private Long userId;

    @BeforeEach
    void setup() {
        // Create a Property
        Property property = new Property();
        property.setName("Test Property");
        property.setAddress("Test Address");
        property.setCity("Test City");
        property.setCountry("Test Country");
        property.setType("Hotel");
        propertyRepository.save(property);
        propertyId = property.getId();

        // Create a RoomType for the Property
        RoomType roomType = new RoomType();
        roomType.setName("Single Room");
        roomType.setNumberOfBeds(1);
        roomType.setNumberOfRooms(10);
        roomType.setGuestCapacity(2);
        roomType.setBasePrice(100.0);
        roomType.setPropertyId(propertyId);
        roomTypeRepository.save(roomType);
        roomTypeId = roomType.getId();

        // Create a User
        User user = new User();
        user.setUsername("testuser");
        user.setName("Test");
        user.setSurname("User");
        user.setEmail("testuser@test.com");
        user.setPassword("password");
        user.setIdentification(12345678);
        user.setRole("CUSTOMER");
        userRepository.save(user);
        userId = user.getId();
    }

    @Test
    void shouldCreateBooking() throws Exception {
        BookingDTO bookingDTO = new BookingDTO(null, LocalDate.now(), LocalDate.now().plusDays(2), 200.0, roomTypeId, userId);

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookingDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.totalPrice", is(200.0)))
                .andExpect(jsonPath("$.roomTypeId", is(roomTypeId.intValue())))
                .andExpect(jsonPath("$.userId", is(userId.intValue())));
    }

    @Test
    void shouldUpdateBooking() throws Exception {
        // Arrange: Create and save a booking
        BookingDTO bookingDTO = bookingService.saveBooking(new BookingDTO(null, LocalDate.now(), LocalDate.now().plusDays(2), 200.0, roomTypeId, userId));

        // Create updated BookingDTO
        BookingDTO updatedBookingDTO = new BookingDTO(null, LocalDate.now(), LocalDate.now().plusDays(3), 300.0, roomTypeId, userId);

        // Act & Assert: Perform the put request
        mockMvc.perform(put("/bookings/{id}", bookingDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedBookingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice", is(300.0)))
                .andExpect(jsonPath("$.roomTypeId", is(roomTypeId.intValue())))
                .andExpect(jsonPath("$.userId", is(userId.intValue())));
    }

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule for LocalDate support
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
