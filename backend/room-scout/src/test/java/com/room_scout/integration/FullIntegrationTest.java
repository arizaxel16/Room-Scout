package com.room_scout.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.room_scout.controller.dto.AddOnDTO;
import com.room_scout.controller.dto.BookingDTO;
import com.room_scout.controller.dto.LoginDTO;
import com.room_scout.controller.dto.PropertyDTO;
import com.room_scout.controller.dto.RoomTypeDTO;
import com.room_scout.controller.dto.UserDTO;
import com.room_scout.model.Property;
import com.room_scout.model.RoomType;
import com.room_scout.model.User;
import com.room_scout.repository.AddOnRepository;
import com.room_scout.repository.BookingRepository;
import com.room_scout.repository.PropertyRepository;
import com.room_scout.repository.RoomTypeRepository;
import com.room_scout.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FullIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private AddOnRepository addOnRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        propertyRepository.deleteAll();
        roomTypeRepository.deleteAll();
        addOnRepository.deleteAll();
        bookingRepository.deleteAll();
    }

    @Test
    void givenUser_whenCreateUser_thenReturnsCreatedUser() throws Exception {
        UserDTO userDTO = new UserDTO(null, "john_doe", 1111111111, "test@mail.com", "john", "doe", "password", "Admin");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.username").value(userDTO.username()));
    }

    @Test
    void givenProperty_whenCreateProperty_thenReturnsCreatedProperty() throws Exception {
        PropertyDTO propertyDTO = new PropertyDTO(null, "Property 1", "My Address", "Colombia", "Bogotá", "Hotel", null, null);

        mockMvc.perform(post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(propertyDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(propertyDTO.name()));
    }

    @Test
    void givenRoomType_whenCreateRoomType_thenReturnsCreatedRoomType() throws Exception {
        // Create a property for the room type
        Property property = new Property();
        property.setName("Test Property");
        property.setAddress("Test Address");
        property.setCountry("Test Country");
        property.setCity("Test City");
        property.setType("Hotel");
        property = propertyRepository.save(property);  // Save and get the generated ID

        RoomTypeDTO roomTypeDTO = new RoomTypeDTO(null, "Simple", 1, 1, 2, 100, property.getId());

        mockMvc.perform(post("/roomtypes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomTypeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void givenAddOn_whenCreateAddOn_thenReturnsCreatedAddOn() throws Exception {
        // Create a property for the add-on
        Property property = new Property();
        property.setName("Test Property");
        property.setAddress("Test Address");
        property.setCountry("Test Country");
        property.setCity("Test City");
        property.setType("Hotel");
        property = propertyRepository.save(property);  // Save and get the generated ID

        AddOnDTO addOnDTO = new AddOnDTO(null, "Champagne", 100, property.getId());

        mockMvc.perform(post("/addons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addOnDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(addOnDTO.name()));
    }

    @Test
    void givenBooking_whenCreateBooking_thenReturnsCreatedBooking() throws Exception {
        // Create user
        User user = new User();
        user.setUsername("john_doe");
        user.setIdentification(1111111111);
        user.setEmail("test@mail.com");
        user.setName("John");
        user.setSurname("Doe");
        user.setPassword("password");
        user.setRole("Admin");
        userRepository.save(user);

        // Create property for booking
        Property property = new Property();
        property.setName("Test Property");
        property.setAddress("Test Address");
        property.setCountry("Test Country");
        property.setCity("Test City");
        property.setType("Hotel");
        property = propertyRepository.save(property);  // Save and get the generated ID

        // Create room type for booking
        RoomType roomType = new RoomType();
        roomType.setName("Simple");
        roomType.setNumberOfBeds(1);
        roomType.setNumberOfRooms(1);
        roomType.setGuestCapacity(2);
        roomType.setBasePrice(500.0);
        roomType.setPropertyId(property.getId());
        roomType = roomTypeRepository.save(roomType);  // Save and get the generated ID

        LocalDate startDate = LocalDate.now(); 
        LocalDate endDate = startDate.plusDays(5); 
        BookingDTO bookingDTO = new BookingDTO(null, startDate, endDate, 500, roomType.getId(), user.getId());

        mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookingDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.roomTypeId").value(roomType.getId()));
    }

    @Test
    void givenUser_whenLoginUser_thenReturnsUserDetails() throws Exception {
        User user = new User();
        user.setUsername("john_doe");
        user.setIdentification(1111111111);
        user.setEmail("test@mail.com");
        user.setName("John");
        user.setSurname("Doe");
        user.setPassword("password");
        user.setRole("Admin");
        userRepository.save(user);

        LoginDTO loginDTO = new LoginDTO("test@mail.com", "password");

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("john_doe"));
    }
}
