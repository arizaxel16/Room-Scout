package com.room_scout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.room_scout.controller.dto.PropertyDTO;
import com.room_scout.model.Property;
import com.room_scout.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PropertyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropertyRepository propertyRepository;

    @BeforeEach
    void setUp() {
        propertyRepository.deleteAll();
    }

    @Test
    void givenValidProperty_whenCreateProperty_thenPropertyIsCreated() throws Exception {
        PropertyDTO newProperty = new PropertyDTO(null, "Test Property", "123 Test St", "Test Country", "Test City", "Hotel", null, null);

        mockMvc.perform(post("/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProperty)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Property"))
                .andExpect(jsonPath("$.address").value("123 Test St"));
    }

    @Test
    void whenGetAllProperties_thenReturnPropertyList() throws Exception {
        Property property = new Property();
        property.setName("Test Property");
        property.setAddress("123 Test St");
        property.setCountry("Test Country");
        property.setCity("Test City");
        property.setType("Hotel");
        propertyRepository.save(property);

        mockMvc.perform(get("/properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Test Property"));
    }

    @Test
    void givenExistingPropertyId_whenGetPropertyById_thenReturnProperty() throws Exception {
        Property property = new Property();
        property.setName("Another Property");
        property.setAddress("456 Another St");
        property.setCountry("Another Country");
        property.setCity("Another City");
        property.setType("Resort");
        property = propertyRepository.save(property);

        mockMvc.perform(get("/properties/{id}", property.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Another Property"));
    }

    @Test
    void givenValidPropertyId_whenUpdateProperty_thenReturnUpdatedProperty() throws Exception {
        Property property = new Property();
        property.setName("Old Property");
        property.setAddress("789 Old St");
        property.setCountry("Old Country");
        property.setCity("Old City");
        property.setType("Motel");
        property = propertyRepository.save(property);

        PropertyDTO updatedProperty = new PropertyDTO(property.getId(), "Updated Property", "789 Updated St", "Updated Country", "Updated City", "Updated Type", null, null);

        mockMvc.perform(put("/properties/{id}", property.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProperty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Property"));
    }

    @Test
    void givenValidId_whenDeleteProperty_thenReturnNoContent() throws Exception {
        Property property = new Property();
        property.setName("Property to Delete");
        property.setAddress("321 Delete St");
        property.setCountry("Delete Country");
        property.setCity("Delete City");
        property.setType("Delete Type");
        property = propertyRepository.save(property);

        mockMvc.perform(delete("/properties/{id}", property.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/properties/{id}", property.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenNonExistentId_whenGetPropertyById_thenReturnNotFound() throws Exception {
        mockMvc.perform(get("/properties/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}
