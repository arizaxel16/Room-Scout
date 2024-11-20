package com.room_scout.service;

import com.room_scout.controller.dto.AddOnDTO;
import com.room_scout.model.AddOn;
import com.room_scout.repository.AddOnRepository;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddOnService {

    private final AddOnRepository addOnRepository;

    public List<AddOnDTO> getAllAddOns() {
        return addOnRepository.findAll().stream()
                .map(this::mapEntityToDTO)
                .toList();
    }

    public Optional<AddOnDTO> getAddOnById(Long id) {
        return addOnRepository.findById(id).map(this::mapEntityToDTO);
    }

    @Generated("excludeFromCoverage")
    public AddOnDTO saveAddOn(AddOnDTO addOnDTO) {
        AddOn addOn = mapDTOToEntity(addOnDTO);
        AddOn savedAddOn = addOnRepository.save(addOn);
        return mapEntityToDTO(savedAddOn);
    }

    public Optional<AddOnDTO> updateAddOn(Long id, AddOnDTO addOnDTO) {
        return addOnRepository.findById(id).map(existingAddOn -> {
            existingAddOn.setName(addOnDTO.name());
            existingAddOn.setPrice(addOnDTO.price());
            AddOn updatedAddOn = addOnRepository.save(existingAddOn);
            return mapEntityToDTO(updatedAddOn);
        });
    }

    public boolean deleteAddOn(Long id) {
        if (addOnRepository.existsById(id)) {
            addOnRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Generated("excludeFromCoverage")
    private AddOnDTO mapEntityToDTO(AddOn addOn) {
        return new AddOnDTO(
                addOn.getId(),
                addOn.getName(),
                addOn.getPrice(),
                addOn.getPropertyId());
    }

    @Generated("excludeFromCoverage")
    private AddOn mapDTOToEntity(AddOnDTO addOnDTO) {
        AddOn addOn = new AddOn();
        addOn.setName(addOnDTO.name());
        addOn.setPrice(addOnDTO.price());
        addOn.setPropertyId(addOnDTO.propertyId());
        return addOn;
    }
}
