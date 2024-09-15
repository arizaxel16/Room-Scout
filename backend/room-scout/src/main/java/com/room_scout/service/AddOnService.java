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

    public Optional<AddOn> getAddOnById(Long id) {
        return addOnRepository.findById(id);
    }

    public List<AddOn> getAllAddOns() {
        return addOnRepository.findAll();
    }

    public void deleteAddOn(Long id) {
        addOnRepository.deleteById(id);
    }

    public List<AddOn> getAddOnsByIds(List<Long> addOnIds) {
        return addOnRepository.findAllById(addOnIds);
    }

    private AddOn mapDtoToEntity(AddOnDTO dto, Property property) {
        AddOn addOn = new AddOn();
        addOn.setName(dto.name());
        addOn.setPrice(dto.price());
        addOn.setProperty(property);
        return addOn;
    }
}
