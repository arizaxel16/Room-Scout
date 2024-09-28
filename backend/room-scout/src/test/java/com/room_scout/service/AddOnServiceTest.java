package com.room_scout.service;

import com.room_scout.controller.dto.AddOnDTO;
import com.room_scout.model.AddOn;
import com.room_scout.repository.AddOnRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddOnServiceTest {

    @Mock
    private AddOnRepository addOnRepository;

    @InjectMocks
    private AddOnService addOnService;

    private AddOnDTO addOnDTO;
    private AddOn addOn;

    @BeforeEach
    void setUp() {
        addOnDTO = new AddOnDTO(1L, "Extra Towel", 5.0, 1L);
        
        addOn = new AddOn();
        addOn.setId(1L);
        addOn.setName("Extra Towel");
        addOn.setPrice(5.0);
        addOn.setPropertyId(1L);
    }

    @Test
    void givenValidAddOn_whenSaveAddOn_thenAddOnIsSaved() {
        when(addOnRepository.save(any(AddOn.class))).thenReturn(addOn);

        AddOnDTO savedAddOn = addOnService.saveAddOn(addOnDTO);

        assertNotNull(savedAddOn);
        assertEquals("Extra Towel", savedAddOn.name());
        verify(addOnRepository).save(any(AddOn.class));
    }

    @Test
    void givenValidId_whenGetAddOnById_thenReturnAddOn() {
        when(addOnRepository.findById(1L)).thenReturn(Optional.of(addOn));

        Optional<AddOnDTO> result = addOnService.getAddOnById(1L);

        assertTrue(result.isPresent());
        assertEquals("Extra Towel", result.get().name());
    }

    @Test
    void givenInvalidId_whenGetAddOnById_thenReturnEmpty() {
        when(addOnRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<AddOnDTO> result = addOnService.getAddOnById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void whenGetAllAddOns_thenReturnAddOnList() {
        when(addOnRepository.findAll()).thenReturn(List.of(addOn));

        List<AddOnDTO> result = addOnService.getAllAddOns();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Extra Towel", result.get(0).name());
    }

    @Test
    void givenValidId_whenUpdateAddOn_thenReturnUpdatedAddOn() {
        AddOnDTO updatedAddOnDTO = new AddOnDTO(1L, "Updated Towel", 10.0, 1L);
        when(addOnRepository.findById(1L)).thenReturn(Optional.of(addOn));
        when(addOnRepository.save(any(AddOn.class))).thenReturn(addOn);

        Optional<AddOnDTO> result = addOnService.updateAddOn(1L, updatedAddOnDTO);

        assertTrue(result.isPresent());
        assertEquals("Updated Towel", result.get().name());
    }

    @Test
    void givenInvalidId_whenUpdateAddOn_thenReturnEmpty() {
        AddOnDTO updatedAddOnDTO = new AddOnDTO(1L, "Updated Towel", 10.0, 1L);
        when(addOnRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<AddOnDTO> result = addOnService.updateAddOn(1L, updatedAddOnDTO);

        assertTrue(result.isEmpty());
    }

    @Test
    void givenValidId_whenDeleteAddOn_thenReturnTrue() {
        when(addOnRepository.existsById(1L)).thenReturn(true);

        boolean isDeleted = addOnService.deleteAddOn(1L);

        assertTrue(isDeleted);
        verify(addOnRepository).deleteById(1L);
    }

    @Test
    void givenInvalidId_whenDeleteAddOn_thenReturnFalse() {
        when(addOnRepository.existsById(1L)).thenReturn(false);

        boolean isDeleted = addOnService.deleteAddOn(1L);

        assertFalse(isDeleted);
        verify(addOnRepository, never()).deleteById(1L);
    }
}
