package com.room_scout.service;

import com.room_scout.controller.dto.PropertyDTO;
import com.room_scout.model.Property;
import com.room_scout.repository.PropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public Property saveProperty(PropertyDTO propertyDTO) {
        Property property = mapDtoToEntity(propertyDTO);
        return propertyRepository.save(property);
    }

    public Optional<PropertyDTO> getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .map(this::mapEntityToResponseDto);
    }

    public List<PropertyDTO> getAllProperties() {
        return propertyRepository.findAll().stream()
                .map(this::mapEntityToResponseDto)
                .toList();
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
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

    private PropertyDTO mapEntityToResponseDto(Property property) {
        return new PropertyDTO(
                property.getId(), 
                property.getName(), 
                property.getAddress(), 
                property.getCountry(), 
                property.getCity(), 
                property.getType()
        );
    }
}

