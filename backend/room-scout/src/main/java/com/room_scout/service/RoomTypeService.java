package com.room_scout.service;

import com.room_scout.controller.dto.RoomTypeDTO;
import com.room_scout.model.Property;
import com.room_scout.model.RoomType;
import com.room_scout.repository.PropertyRepository;
import com.room_scout.repository.RoomTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    public RoomType saveRoomType(RoomTypeDTO roomTypeDTO) {
        Property property = propertyRepository.findById(roomTypeDTO.propertyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid property ID"));

        return mapDtoToEntity(roomTypeDTO, property);
    }

    public Optional<RoomTypeDTO> getRoomTypeById(Long id) {
        return roomTypeRepository.findById(id)
                .map(this::mapEntityToResponseDto);
    }

    public List<RoomTypeDTO> getAllRoomTypes() {
        return roomTypeRepository.findAll()
                .stream()
                .map(this::mapEntityToResponseDto)
                .toList();
    }

    public void deleteRoomType(Long id) {
        roomTypeRepository.deleteById(id);
    }

    // DTO Object => Model Entity
    // Returns entity with stablished property relation
    private RoomType mapDtoToEntity(RoomTypeDTO dto, Property property) {
        RoomType roomType = new RoomType();
        roomType.setName(dto.name());
        roomType.setNumberOfBeds(dto.numberOfBeds());
        roomType.setGuestCapacity(dto.guestCapacity());
        roomType.setBasePrice(dto.basePrice());
        roomType.setProperty(property);
        return roomType;
    }

    // Model Entity => Response DTO Object
    // Returns with simple property ID
    private RoomTypeDTO mapEntityToResponseDto(RoomType roomType) {
        return new RoomTypeDTO(
            roomType.getId(), 
            roomType.getName(), 
            roomType.getNumberOfBeds(), 
            roomType.getGuestCapacity(),
            roomType.getBasePrice(), 
            roomType.getProperty().getId());
    }

    public RoomType updateRoomType(Long id, RoomTypeDTO roomTypeDTO) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RoomType not found with ID: " + id));

        Property property = propertyRepository.findById(roomTypeDTO.propertyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid property ID"));

        roomType.setName(roomTypeDTO.name());
        roomType.setNumberOfBeds(roomTypeDTO.numberOfBeds());
        roomType.setGuestCapacity(roomTypeDTO.guestCapacity());
        roomType.setBasePrice(roomTypeDTO.basePrice());
        roomType.setProperty(property);

        return roomTypeRepository.save(roomType);
    }
}

