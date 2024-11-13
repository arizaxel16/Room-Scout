package com.room_scout.service;

import com.room_scout.controller.dto.RoomTypeDTO;
import com.room_scout.model.RoomType;
import com.room_scout.repository.RoomTypeRepository;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

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
                    existingRoomType.setNumberOfRooms(roomTypeDTO.numberOfRooms());
                    existingRoomType.setGuestCapacity(roomTypeDTO.guestCapacity());
                    existingRoomType.setBasePrice(roomTypeDTO.basePrice());
                    existingRoomType.setPropertyId(roomTypeDTO.propertyId());
                    roomTypeRepository.save(existingRoomType);
                    return mapEntityToDTO(existingRoomType);
                });
    }

    @Generated("excludeFromCoverage")
    public void deleteAll() {
        roomTypeRepository.deleteAll();
    }

    @Generated("excludeFromCoverage")
    private RoomTypeDTO mapEntityToDTO(RoomType roomType) {
        return new RoomTypeDTO(
                roomType.getId(),
                roomType.getName(),
                roomType.getNumberOfBeds(),
                roomType.getNumberOfRooms(),
                roomType.getGuestCapacity(),
                roomType.getBasePrice(),
                roomType.getPropertyId()
        );
    }

    @Generated("excludeFromCoverage")
    private RoomType mapDtoToEntity(RoomTypeDTO roomTypeDTO) {
        RoomType roomType = new RoomType();
        roomType.setName(roomTypeDTO.name());
        roomType.setNumberOfBeds(roomTypeDTO.numberOfBeds());
        roomType.setNumberOfRooms(roomTypeDTO.numberOfRooms());
        roomType.setGuestCapacity(roomTypeDTO.guestCapacity());
        roomType.setBasePrice(roomTypeDTO.basePrice());
        roomType.setPropertyId(roomTypeDTO.propertyId());
        return roomType;
    }
}
