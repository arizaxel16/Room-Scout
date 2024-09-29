package com.room_scout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.room_scout.controller.dto.RoomTypeDTO;
import com.room_scout.model.Property;
import com.room_scout.model.RoomType;
import com.room_scout.repository.PropertyRepository;
import com.room_scout.repository.RoomTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RoomTypeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private Property testProperty;

    @BeforeEach
    void setUp() {
        roomTypeRepository.deleteAll();
        propertyRepository.deleteAll();

        testProperty = new Property();
        testProperty.setName("Test Property");
        testProperty.setAddress("123 Test St");
        testProperty.setCountry("Test Country");
        testProperty.setCity("Test City");
        testProperty.setType("Hotel");
        propertyRepository.save(testProperty);
    }

    @Test
    void givenValidRoomType_whenCreateRoomType_thenRoomTypeIsCreated() throws Exception {

        RoomTypeDTO newRoomType = new RoomTypeDTO(null, "Single Room", 1, 10, 2, 100.0, testProperty.getId());

        mockMvc.perform(post("/roomtypes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRoomType)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Single Room"))
                .andExpect(jsonPath("$.numberOfBeds").value(1))
                .andExpect(jsonPath("$.propertyId").value(testProperty.getId()));
    }

    @Test
    void whenGetAllRoomTypes_thenReturnRoomTypeList() throws Exception {

        RoomType roomType = new RoomType();
        roomType.setName("Single Room");
        roomType.setNumberOfBeds(1);
        roomType.setNumberOfRooms(10);
        roomType.setGuestCapacity(2);
        roomType.setBasePrice(100.0);
        roomType.setPropertyId(testProperty.getId());
        roomTypeRepository.save(roomType);

        mockMvc.perform(get("/roomtypes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Single Room"));
    }

    @Test
    void givenExistingRoomTypeId_whenGetRoomTypeById_thenReturnRoomType() throws Exception {

        RoomType roomType = new RoomType();
        roomType.setName("Suite");
        roomType.setNumberOfBeds(2);
        roomType.setNumberOfRooms(5);
        roomType.setGuestCapacity(4);
        roomType.setBasePrice(200.0);
        roomType.setPropertyId(testProperty.getId());
        roomType = roomTypeRepository.save(roomType);

        mockMvc.perform(get("/roomtypes/{id}", roomType.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Suite"))
                .andExpect(jsonPath("$.propertyId").value(testProperty.getId()));
    }

    @Test
    void givenValidRoomTypeId_whenUpdateRoomType_thenReturnUpdatedRoomType() throws Exception {

        RoomType roomType = new RoomType();
        roomType.setName("Standard Room");
        roomType.setNumberOfBeds(1);
        roomType.setNumberOfRooms(15);
        roomType.setGuestCapacity(3);
        roomType.setBasePrice(150.0);
        roomType.setPropertyId(testProperty.getId());
        roomType = roomTypeRepository.save(roomType);

        RoomTypeDTO updatedRoomType = new RoomTypeDTO(roomType.getId(), "Deluxe Room", 2, 20, 5, 250.0,
                testProperty.getId());

        mockMvc.perform(put("/roomtypes/{id}", roomType.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRoomType)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Deluxe Room"));
    }

    @Test
    void givenValidId_whenDeleteRoomType_thenReturnNoContent() throws Exception {

        RoomType roomType = new RoomType();
        roomType.setName("Room to Delete");
        roomType.setNumberOfBeds(1);
        roomType.setNumberOfRooms(8);
        roomType.setGuestCapacity(2);
        roomType.setBasePrice(80.0);
        roomType.setPropertyId(testProperty.getId());
        roomType = roomTypeRepository.save(roomType);

        mockMvc.perform(delete("/roomtypes/{id}", roomType.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/roomtypes/{id}", roomType.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenNonExistentRoomTypeId_whenGetRoomTypeById_thenReturnNotFound() throws Exception {

        mockMvc.perform(get("/roomtypes/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}
