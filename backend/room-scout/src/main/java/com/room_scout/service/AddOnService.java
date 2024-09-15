package com.room_scout.service;

import com.room_scout.controller.dto.AddOnDTO;
import com.room_scout.model.AddOn;
import com.room_scout.model.Property;
import com.room_scout.repository.AddOnRepository;
import com.room_scout.repository.PropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddOnService {

    @Autowired
    private AddOnRepository addOnRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    public AddOn saveAddOn(AddOnDTO addOnDTO) {
        Property property = propertyRepository.findById(addOnDTO.propertyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid property ID"));

        AddOn addOn = mapDtoToEntity(addOnDTO, property);
        return addOnRepository.save(addOn);
    }

    public Optional<AddOnDTO> getAddOnById(Long id) {
        return addOnRepository.findById(id)
                .map(this::mapEntityToResponseDto);
    }

    public List<AddOnDTO> getAllAddOns() {
        return addOnRepository.findAll()
                .stream()
                .map(this::mapEntityToResponseDto)
                .toList();
    }

    public void deleteAddOn(Long id) {
        addOnRepository.deleteById(id);
    }

    public List<AddOn> getAddOnsByIds(List<Long> addOnIds) {
        return addOnRepository.findAllById(addOnIds);
    }

    // DTO Object => Model Entity
    // Returns entity with stablished property relation
    private AddOn mapDtoToEntity(AddOnDTO dto, Property property) {
        AddOn addOn = new AddOn();
        addOn.setName(dto.name());
        addOn.setPrice(dto.price());
        addOn.setProperty(property);
        return addOn;
    }

    // Model Entity => Response DTO Object
    // Returns with simple property ID
    private AddOnDTO mapEntityToResponseDto(AddOn addOn) {
        return new AddOnDTO(
            addOn.getId(), 
            addOn.getName(), 
            addOn.getPrice(),
            addOn.getProperty().getId());
    }
}
