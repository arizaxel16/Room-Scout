package com.room_scout.controller;

import com.room_scout.controller.dto.BookingDTO;
import com.room_scout.controller.dto.PropertyDTO;
import com.room_scout.controller.dto.RoomTypeDTO;
import com.room_scout.controller.dto.UserDTO;
import com.room_scout.service.BookingService;
import com.room_scout.service.PropertyService;
import com.room_scout.service.RoomTypeService;
import com.room_scout.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
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
    private PropertyService propertyService;

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private UserService userService;

    private Long roomTypeId;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    public void setUp() {

        UserDTO userDTO = new UserDTO(null, "testuser", 123456, "testuser@example.com", "Test", "User", "password",
                "ROLE_USER");
        userId = userService.saveUser(userDTO).id();

        PropertyDTO propertyDTO = new PropertyDTO(null, "Test Hotel", "123 Test St", "Test Country", "Test City",
                "Hotel", Collections.emptyList(), Collections.emptyList());
        Long propertyId = propertyService.saveProperty(propertyDTO).id();

        RoomTypeDTO roomTypeDTO = new RoomTypeDTO(null, "Deluxe Room", 2, 5, 4, 100.0, propertyId);
        roomTypeId = roomTypeService.saveRoomType(roomTypeDTO).id();

        startDate = LocalDate.now().plusDays(5);
        endDate = LocalDate.now().plusDays(10);
    }

    @Test
    public void testCreateBooking() throws Exception {

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"startDate\": \"" + startDate + "\",\n" +
                        "  \"endDate\": \"" + endDate + "\",\n" +
                        "  \"totalPrice\": 500.0,\n" +
                        "  \"roomTypeId\": " + roomTypeId + ",\n" +
                        "  \"userId\": " + userId + "\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.startDate", is(startDate.toString())))
                .andExpect(jsonPath("$.endDate", is(endDate.toString())))
                .andExpect(jsonPath("$.totalPrice", is(500.0)))
                .andExpect(jsonPath("$.roomTypeId", is(roomTypeId.intValue())))
                .andExpect(jsonPath("$.userId", is(userId.intValue())));
    }

    @Test
    public void testGetBookingById() throws Exception {

        BookingDTO bookingDTO = new BookingDTO(null, startDate, endDate, 500.0, roomTypeId, userId);
        Long bookingId = bookingService.saveBooking(bookingDTO).id();

        mockMvc.perform(get("/bookings/{id}", bookingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startDate", is(startDate.toString())))
                .andExpect(jsonPath("$.endDate", is(endDate.toString())))
                .andExpect(jsonPath("$.totalPrice", is(500.0)))
                .andExpect(jsonPath("$.roomTypeId", is(roomTypeId.intValue())))
                .andExpect(jsonPath("$.userId", is(userId.intValue())));
    }

    @Test
    public void testDeleteBooking() throws Exception {

        BookingDTO bookingDTO = new BookingDTO(null, startDate, endDate, 500.0, roomTypeId, userId);
        Long bookingId = bookingService.saveBooking(bookingDTO).id();

        mockMvc.perform(delete("/bookings/{id}", bookingId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/bookings/{id}", bookingId))
                .andExpect(status().isNotFound());
    }
}
