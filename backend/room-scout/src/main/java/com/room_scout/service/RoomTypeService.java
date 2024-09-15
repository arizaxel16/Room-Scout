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

        RoomType roomType = mapDtoToEntity(roomTypeDTO, property);
        return roomTypeRepository.save(roomType);
    }

    public Optional<RoomType> getRoomTypeById(Long id) {
        return roomTypeRepository.findById(id);
    }

    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAll();
    }

    public void deleteRoomType(Long id) {
        roomTypeRepository.deleteById(id);
    }

    private RoomType mapDtoToEntity(RoomTypeDTO dto, Property property) {
        RoomType roomType = new RoomType();
        roomType.setName(dto.name());
        roomType.setNumberOfBeds(dto.numberOfBeds());
        roomType.setGuestCapacity(dto.guestCapacity());
        roomType.setFloorLevel(dto.floorLevel());
        roomType.setBasePrice(dto.basePrice());
        roomType.setProperty(property);
        return roomType;
    }
}
