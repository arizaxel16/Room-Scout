package com.room_scout.service;

import com.room_scout.controller.dto.AddOnDTO;
import com.room_scout.controller.dto.PropertyDTO;
import com.room_scout.controller.dto.RoomTypeDTO;
import com.room_scout.model.AddOn;
import com.room_scout.model.Property;
import com.room_scout.model.RoomType;
import com.room_scout.repository.PropertyRepository;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Generated("excludeFromCoverage")
    public Property mapDtoToEntity(PropertyDTO propertyDTO) {
        Property property = new Property();
        property.setName(propertyDTO.name());
        property.setAddress(propertyDTO.address());
        property.setCountry(propertyDTO.country());
        property.setCity(propertyDTO.city());
        property.setType(propertyDTO.type());

        List<RoomType> roomTypes = propertyDTO.roomTypes().stream()
                .map(this::mapRoomTypeDtoToEntity)
                .toList();
        property.setRoomTypes(roomTypes);

        List<AddOn> addOns = propertyDTO.addOns().stream()
                .map(this::mapAddOnDtoToEntity)
                .toList();
        property.setAddOns(addOns);

        return property;
    }

    @Generated("excludeFromCoverage")
    private RoomTypeDTO mapRoomTypeToDTO(RoomType roomType) {
        return new RoomTypeDTO(
                roomType.getId(),
                roomType.getName(),
                roomType.getNumberOfBeds(),
                roomType.getNumberOfRooms(),
                roomType.getGuestCapacity(),
                roomType.getBasePrice(),
                roomType.getPropertyId());
    }

    @Generated("excludeFromCoverage")
    private RoomType mapRoomTypeDtoToEntity(RoomTypeDTO roomTypeDTO) {
        RoomType roomType = new RoomType();
        roomType.setId(roomTypeDTO.id());
        roomType.setName(roomTypeDTO.name());
        roomType.setNumberOfBeds(roomTypeDTO.numberOfBeds());
        roomType.setGuestCapacity(roomTypeDTO.guestCapacity());
        roomType.setBasePrice(roomTypeDTO.basePrice());
        roomType.setPropertyId(roomTypeDTO.propertyId());
        return roomType;
    }

    @Generated("excludeFromCoverage")
    private AddOnDTO mapAddOnToDTO(AddOn addOn) {
        return new AddOnDTO(
                addOn.getId(),
                addOn.getName(),
                addOn.getPrice(),
                addOn.getPropertyId());
    }

    @Generated("excludeFromCoverage")
    private AddOn mapAddOnDtoToEntity(AddOnDTO addOnDTO) {
        AddOn addOn = new AddOn();
        addOn.setId(addOnDTO.id());
        addOn.setName(addOnDTO.name());
        addOn.setPrice(addOnDTO.price());
        addOn.setPropertyId(addOnDTO.propertyId());
        return addOn;
    }

    private List<RoomTypeDTO> mapRoomTypesToDTOs(List<RoomType> roomTypes) {
        return roomTypes.stream()
                .map(this::mapRoomTypeToDTO)
                .toList();
    }

    @Generated("excludeFromCoverage")
    private List<AddOnDTO> mapAddOnsToDTOs(List<AddOn> addOns) {
        return addOns.stream()
                .map(this::mapAddOnToDTO)
                .toList();
    }

    @Generated("excludeFromCoverage")
    private PropertyDTO mapEntityToResponseDto(Property property) {
        return new PropertyDTO(
                property.getId(),
                property.getName(),
                property.getAddress(),
                property.getCountry(),
                property.getCity(),
                property.getType(),
                mapRoomTypesToDTOs(property.getRoomTypes()),
                mapAddOnsToDTOs(property.getAddOns()));
    }

    public List<PropertyDTO> getPropertyByType(String type) {
        List<Property> properties = propertyRepository.findByType(type);
        if (properties.isEmpty()) {
            throw new IllegalArgumentException("No properties found for type: " + type);
        }
        return properties.stream()
                .map(this::mapEntityToResponseDto)
                .toList();
    }
}
