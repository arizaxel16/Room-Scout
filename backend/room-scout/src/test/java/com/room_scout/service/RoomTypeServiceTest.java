package com.room_scout.service;

import com.room_scout.controller.dto.RoomTypeDTO;
import com.room_scout.model.RoomType;
import com.room_scout.repository.RoomTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class RoomTypeServiceTest {

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @InjectMocks
    private RoomTypeService roomTypeService;

    private RoomTypeDTO roomTypeDTO;
    private RoomType roomType;

    @BeforeEach
    void setUp() {
        roomTypeDTO = new RoomTypeDTO(1L, "Deluxe Room", 2, 1, 4, 150.0, 1L);
        
        roomType = new RoomType();
        roomType.setId(1L);
        roomType.setName("Deluxe Room");
        roomType.setNumberOfBeds(2);
        roomType.setNumberOfRooms(1);
        roomType.setGuestCapacity(4);
        roomType.setBasePrice(150.0);
        roomType.setPropertyId(1L);
    }

    @Test
    void givenValidRoomType_whenSaveRoomType_thenRoomTypeIsSaved() {
        when(roomTypeRepository.save(any(RoomType.class))).thenReturn(roomType);

        RoomTypeDTO savedRoomType = roomTypeService.saveRoomType(roomTypeDTO);

        assertNotNull(savedRoomType);
        assertEquals("Deluxe Room", savedRoomType.name());
        verify(roomTypeRepository).save(any(RoomType.class));
    }

    @Test
    void givenValidId_whenGetRoomTypeById_thenReturnRoomType() {
        when(roomTypeRepository.findById(1L)).thenReturn(Optional.of(roomType));

        Optional<RoomTypeDTO> result = roomTypeService.getRoomTypeById(1L);

        assertTrue(result.isPresent());
        assertEquals("Deluxe Room", result.get().name());
    }

    @Test
    void givenInvalidId_whenGetRoomTypeById_thenReturnEmpty() {
        when(roomTypeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<RoomTypeDTO> result = roomTypeService.getRoomTypeById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void whenGetAllRoomTypes_thenReturnRoomTypeList() {
        when(roomTypeRepository.findAll()).thenReturn(List.of(roomType));

        List<RoomTypeDTO> result = roomTypeService.getAllRoomTypes();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Deluxe Room", result.get(0).name());
    }

    @Test
    void givenValidId_whenUpdateRoomType_thenReturnUpdatedRoomType() {
        RoomTypeDTO updatedRoomTypeDTO = new RoomTypeDTO(1L, "Updated Room", 2, 1, 4, 200.0, 1L);
        when(roomTypeRepository.findById(1L)).thenReturn(Optional.of(roomType));
        when(roomTypeRepository.save(any(RoomType.class))).thenReturn(roomType);

        Optional<RoomTypeDTO> result = roomTypeService.updateRoomType(1L, updatedRoomTypeDTO);

        assertTrue(result.isPresent());
        assertEquals("Updated Room", result.get().name());
    }

    @Test
    void givenInvalidId_whenUpdateRoomType_thenReturnEmpty() {
        RoomTypeDTO updatedRoomTypeDTO = new RoomTypeDTO(1L, "Updated Room", 2, 1, 4, 200.0, 1L);
        when(roomTypeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<RoomTypeDTO> result = roomTypeService.updateRoomType(1L, updatedRoomTypeDTO);

        assertTrue(result.isEmpty());
    }

    @Test
    void givenValidId_whenDeleteRoomType_thenReturnTrue() {
        when(roomTypeRepository.existsById(1L)).thenReturn(true);

        boolean isDeleted = roomTypeService.deleteRoomType(1L);

        assertTrue(isDeleted);
        verify(roomTypeRepository).deleteById(1L);
    }

    @Test
    void givenInvalidId_whenDeleteRoomType_thenReturnFalse() {
        when(roomTypeRepository.existsById(1L)).thenReturn(false);

        boolean isDeleted = roomTypeService.deleteRoomType(1L);

        assertFalse(isDeleted);
        verify(roomTypeRepository, never()).deleteById(1L);
    }
}
