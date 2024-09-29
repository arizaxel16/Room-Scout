package com.room_scout.service;

import com.room_scout.controller.dto.AddOnDTO;
import com.room_scout.controller.dto.PropertyDTO;
import com.room_scout.controller.dto.RoomTypeDTO;
import com.room_scout.model.Property;
import com.room_scout.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    private PropertyDTO propertyDTO;
    private Property property;

    @BeforeEach
    void setUp() {
        List<RoomTypeDTO> roomTypes = new ArrayList<>();
        roomTypes.add(new RoomTypeDTO(1L, "Single Room", 1, 1, 2, 100.0, null));

        List<AddOnDTO> addOns = new ArrayList<>();
        addOns.add(new AddOnDTO(1L, "Breakfast", 15.0, null));

        propertyDTO = new PropertyDTO(1L, "Beach House", "123 Ocean Drive", "USA", "Miami", "House", roomTypes, addOns);
        
        property = new Property();
        property.setId(1L);
        property.setName("Beach House");
        property.setAddress("123 Ocean Drive");
        property.setCountry("USA");
        property.setCity("Miami");
        property.setType("House");
        property.setRoomTypes(new ArrayList<>());
        property.setAddOns(new ArrayList<>());
    }

    @Test
    void givenValidProperty_whenSaveProperty_thenPropertyIsSaved() {
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        PropertyDTO savedProperty = propertyService.saveProperty(propertyDTO);

        assertNotNull(savedProperty);
        assertEquals("Beach House", savedProperty.name());
        verify(propertyRepository).save(any(Property.class));
    }

    @Test
    void givenValidId_whenGetPropertyById_thenReturnProperty() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        PropertyDTO result = propertyService.getPropertyById(1L);

        assertNotNull(result);
        assertEquals("Beach House", result.name());
    }

    @Test
    void givenInvalidId_whenGetPropertyById_thenThrowException() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> propertyService.getPropertyById(1L));
        assertEquals("Property not found with ID: 1", exception.getMessage());
    }

    @Test
    void whenGetAllProperties_thenReturnPropertyList() {
        when(propertyRepository.findAll()).thenReturn(List.of(property));

        List<PropertyDTO> result = propertyService.getAllProperties();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Beach House", result.get(0).name());
    }

    @Test
    void givenValidId_whenUpdateProperty_thenReturnUpdatedProperty() {
        PropertyDTO updatedPropertyDTO = new PropertyDTO(1L, "Updated House", "456 Ocean Drive", "USA", "Miami", "House", new ArrayList<>(), new ArrayList<>());
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        PropertyDTO result = propertyService.updateProperty(1L, updatedPropertyDTO);

        assertEquals("Updated House", result.name());
        assertEquals("456 Ocean Drive", result.address());
    }

    @Test
    void givenInvalidId_whenUpdateProperty_thenThrowException() {
        PropertyDTO updatedPropertyDTO = new PropertyDTO(1L, "Updated House", "456 Ocean Drive", "USA", "Miami", "House", new ArrayList<>(), new ArrayList<>());
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> propertyService.updateProperty(1L, updatedPropertyDTO));
        assertEquals("Property not found with ID: 1", exception.getMessage());
    }

    @Test
    void givenValidId_whenDeleteProperty_thenReturnTrue() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        boolean isDeleted = propertyService.deleteProperty(1L);

        assertTrue(isDeleted);
        verify(propertyRepository).deleteById(1L);
    }

    @Test
    void givenInvalidId_whenDeleteProperty_thenReturnFalse() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.empty());

        boolean isDeleted = propertyService.deleteProperty(1L);

        assertFalse(isDeleted);
        verify(propertyRepository, never()).deleteById(1L);
    }

    @Test
    void givenValidType_whenGetPropertyByType_thenReturnPropertyList() {
        when(propertyRepository.findByType("House")).thenReturn(List.of(property));

        List<PropertyDTO> result = propertyService.getPropertyByType("House");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Beach House", result.get(0).name());
    }

    @Test
    void givenInvalidType_whenGetPropertyByType_thenThrowException() {
        when(propertyRepository.findByType("Villa")).thenReturn(List.of());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> propertyService.getPropertyByType("Villa"));
        assertEquals("No properties found for type: Villa", exception.getMessage());
    }
}
