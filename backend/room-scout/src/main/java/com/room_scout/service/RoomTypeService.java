package com.room_scout.service;

import com.room_scout.controller.dto.RoomTypeDTO;
import com.room_scout.model.RoomType;
import com.room_scout.repository.PropertyRepository;
import com.room_scout.repository.RoomTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    private final PropertyRepository propertyRepository;

    public RoomTypeDTO saveRoomType(RoomTypeDTO roomTypeDTO) {
        RoomType roomType = mapDtoToEntity(roomTypeDTO);
        RoomType savedRoomType = roomTypeRepository.save(roomType);
        return mapEntityToDTO(savedRoomType);
    }

    public Optional<RoomTypeDTO> getRoomTypeById(Long id) {
        return roomTypeRepository.findById(id)
                .map(this::mapEntityToDTO);
    }

    public List<RoomTypeDTO> getAllRoomTypes() {
        return roomTypeRepository.findAll().stream()
                .map(this::mapEntityToDTO)
                .toList();
    }

    public boolean deleteRoomType(Long id) {
        if (roomTypeRepository.existsById(id)) {
            roomTypeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<RoomTypeDTO> updateRoomType(Long id, RoomTypeDTO roomTypeDTO) {
        return roomTypeRepository.findById(id)
                .map(existingRoomType -> {
                    existingRoomType.setName(roomTypeDTO.name());
                    existingRoomType.setNumberOfBeds(roomTypeDTO.numberOfBeds());
                    existingRoomType.setGuestCapacity(roomTypeDTO.guestCapacity());
                    existingRoomType.setBasePrice(roomTypeDTO.basePrice());
                    existingRoomType.setProperty(propertyRepository.findById(roomTypeDTO.propertyId())
                            .orElseThrow(() -> new IllegalArgumentException("Property not found")));
                    roomTypeRepository.save(existingRoomType);
                    return mapEntityToDTO(existingRoomType);
                });
    }

    private RoomTypeDTO mapEntityToDTO(RoomType roomType) {
        return new RoomTypeDTO(
                roomType.getId(),
                roomType.getName(),
                roomType.getNumberOfBeds(),
                roomType.getGuestCapacity(),
                roomType.getBasePrice(),
                roomType.getProperty().getId()
        );
    }

    private RoomType mapDtoToEntity(RoomTypeDTO roomTypeDTO) {
        RoomType roomType = new RoomType();
        roomType.setName(roomTypeDTO.name());
        roomType.setNumberOfBeds(roomTypeDTO.numberOfBeds());
        roomType.setGuestCapacity(roomTypeDTO.guestCapacity());
        roomType.setBasePrice(roomTypeDTO.basePrice());
        roomType.setProperty(propertyRepository.findById(roomTypeDTO.propertyId())
                .orElseThrow(() -> new IllegalArgumentException("Property not found")));
        return roomType;
    }
}
