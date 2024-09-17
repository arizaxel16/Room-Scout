package com.room_scout.service;

import com.room_scout.controller.dto.AddOnDTO;
import com.room_scout.controller.dto.PropertyDTO;
import com.room_scout.controller.dto.RoomTypeDTO;
import com.room_scout.model.AddOn;
import com.room_scout.model.Property;
import com.room_scout.model.RoomType;
import com.room_scout.repository.PropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyDTO saveProperty(PropertyDTO propertyDTO) {
        Property property = mapDtoToEntity(propertyDTO);
        Property savedProperty = propertyRepository.save(property);
        return mapEntityToResponseDto(savedProperty);
    }

    public PropertyDTO getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with ID: " + id));
        return mapEntityToResponseDto(property);
    }

    public List<PropertyDTO> getAllProperties() {
        return propertyRepository.findAll().stream()
                .map(this::mapEntityToResponseDto)
                .toList();
    }

    public boolean deleteProperty(Long id) {
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isPresent()) {
            propertyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public PropertyDTO updateProperty(Long id, PropertyDTO propertyDTO) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with ID: " + id));
        property.setName(propertyDTO.name());
        property.setAddress(propertyDTO.address());
        property.setCountry(propertyDTO.country());
        property.setCity(propertyDTO.city());
        property.setType(propertyDTO.type());
        Property updatedProperty = propertyRepository.save(property);
        return mapEntityToResponseDto(updatedProperty);
    }

    private Property mapDtoToEntity(PropertyDTO dto) {
        Property property = new Property();
        property.setName(dto.name());
        property.setAddress(dto.address());
        property.setCountry(dto.country());
        property.setCity(dto.city());
        property.setType(dto.type());
        return property;
    }

        private RoomTypeDTO mapRoomTypeToDTO(RoomType roomType) {
            return new RoomTypeDTO(
                roomType.getId(),
                roomType.getName(),
                roomType.getNumberOfBeds(),
                roomType.getGuestCapacity(),
                roomType.getBasePrice(),
                roomType.getProperty().getId()
            );
        }
    
        private AddOnDTO mapAddOnToDTO(AddOn addOn) {
            return new AddOnDTO(addOn.getId(), addOn.getName(), addOn.getPrice(), addOn.getProperty().getId());
        }

    private List<RoomTypeDTO> mapRoomTypesToDTOs(List<RoomType> roomTypes) {
        return roomTypes.stream()
                        .map(this::mapRoomTypeToDTO)
                        .collect(Collectors.toList());
    }

    private List<AddOnDTO> mapAddOnsToDTOs(List<AddOn> addOns) {
        return addOns.stream()
                    .map(this::mapAddOnToDTO)
                    .collect(Collectors.toList());
    }

    private PropertyDTO mapEntityToResponseDto(Property property) {
        return new PropertyDTO(
                property.getId(),
                property.getName(),
                property.getAddress(),
                property.getCountry(),
                property.getCity(),
                property.getType(),
                mapRoomTypesToDTOs(property.getRoomTypes()),
                mapAddOnsToDTOs(property.getAddOns())
        );
    }
}
