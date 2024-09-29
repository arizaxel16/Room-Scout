package com.room_scout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.room_scout.controller.dto.AddOnDTO;
import com.room_scout.model.AddOn;
import com.room_scout.model.Property;
import com.room_scout.repository.AddOnRepository;
import com.room_scout.repository.PropertyRepository;
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
class AddOnControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AddOnRepository addOnRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private Property testProperty;

    @BeforeEach
    void setUp() {
        addOnRepository.deleteAll();
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
    void givenValidAddOn_whenCreateAddOn_thenAddOnIsCreated() throws Exception {

        AddOnDTO newAddOn = new AddOnDTO(null, "Breakfast", 15.0, testProperty.getId());

        mockMvc.perform(post("/addons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAddOn)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Breakfast"))
                .andExpect(jsonPath("$.price").value(15.0))
                .andExpect(jsonPath("$.propertyId").value(testProperty.getId()));
    }

    @Test
    void whenGetAllAddOns_thenReturnAddOnList() throws Exception {

        AddOn addOn = new AddOn();
        addOn.setName("Spa Access");
        addOn.setPrice(50.0);
        addOn.setPropertyId(testProperty.getId());
        addOnRepository.save(addOn);

        mockMvc.perform(get("/addons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Spa Access"));
    }

    @Test
    void givenExistingAddOnId_whenGetAddOnById_thenReturnAddOn() throws Exception {

        AddOn addOn = new AddOn();
        addOn.setName("WiFi");
        addOn.setPrice(5.0);
        addOn.setPropertyId(testProperty.getId());
        addOn = addOnRepository.save(addOn);

        mockMvc.perform(get("/addons/{id}", addOn.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("WiFi"))
                .andExpect(jsonPath("$.propertyId").value(testProperty.getId()));
    }

    @Test
    void givenValidAddOnId_whenUpdateAddOn_thenReturnUpdatedAddOn() throws Exception {

        AddOn addOn = new AddOn();
        addOn.setName("Room Cleaning");
        addOn.setPrice(25.0);
        addOn.setPropertyId(testProperty.getId());
        addOn = addOnRepository.save(addOn);

        AddOnDTO updatedAddOn = new AddOnDTO(addOn.getId(), "Laundry Service", 30.0, testProperty.getId());

        mockMvc.perform(put("/addons/{id}", addOn.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAddOn)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laundry Service"));
    }

    @Test
    void givenValidId_whenDeleteAddOn_thenReturnNoContent() throws Exception {

        AddOn addOn = new AddOn();
        addOn.setName("Airport Shuttle");
        addOn.setPrice(20.0);
        addOn.setPropertyId(testProperty.getId());
        addOn = addOnRepository.save(addOn);

        mockMvc.perform(delete("/addons/{id}", addOn.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/addons/{id}", addOn.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenNonExistentAddOnId_whenGetAddOnById_thenReturnNotFound() throws Exception {

        mockMvc.perform(get("/addons/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}
