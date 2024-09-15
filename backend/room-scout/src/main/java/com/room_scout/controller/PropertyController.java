package com.room_scout.controller;

import com.room_scout.controller.dto.PropertyDTO;
import com.room_scout.model.Property;
import com.room_scout.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/properties")
@AllArgsConstructor
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDTO> getPropertyById(@PathVariable Long id) {
        Optional<PropertyDTO> property = propertyService.getPropertyById(id);
        return property.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Property> createProperty(@RequestBody PropertyDTO propertyDTO) {
        Property savedProperty = propertyService.saveProperty(propertyDTO);
        return ResponseEntity.ok(savedProperty);
    }
}
