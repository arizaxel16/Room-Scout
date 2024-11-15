package com.room_scout.controller;

import com.room_scout.controller.dto.PropertyDTO;
import com.room_scout.service.PropertyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/properties")
@AllArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000", "http://157.173.114.224:3000" })
@Tag(name = "Property Management", description = "API for managing properties")
public class PropertyController {

    private final PropertyService propertyService;

    @Operation(summary = "Retrieve all properties", description = "Fetch a list of all available properties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of properties"),
            @ApiResponse(responseCode = "204", description = "No properties found")
    })
    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getAllProperties() {
        List<PropertyDTO> properties = propertyService.getAllProperties();
        if (properties.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(properties);
    }

    @Operation(summary = "Retrieve a property by ID", description = "Fetch details of a specific property using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved property details"),
            @ApiResponse(responseCode = "404", description = "Property not found with the provided ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PropertyDTO> getPropertyById(@PathVariable Long id) {
        try {
            PropertyDTO property = propertyService.getPropertyById(id);
            return ResponseEntity.ok(property);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Retrieve properties by type", description = "Fetch a list of properties filtered by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved properties by type"),
            @ApiResponse(responseCode = "404", description = "No properties found for the specified type")
    })
    @GetMapping("/type/{type}")
    public ResponseEntity<List<PropertyDTO>> getPropertyByType(@PathVariable String type) {
        try {
            List<PropertyDTO> properties = propertyService.getPropertyByType(type);
            return ResponseEntity.ok(properties);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Create a new property", description = "Add a new property to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new property"),
            @ApiResponse(responseCode = "400", description = "Invalid input for creating a property")
    })
    @PostMapping
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyDTO propertyDTO) {
        PropertyDTO savedProperty = propertyService.saveProperty(propertyDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProperty.id())
                .toUri();
        return ResponseEntity.created(location).body(savedProperty);
    }

    @Operation(summary = "Bulk create properties", description = "Add multiple properties at once")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created properties in bulk"),
            @ApiResponse(responseCode = "400", description = "Invalid input for creating properties")
    })
    @PostMapping("/bulk")
    public void createProperties(@RequestBody List<PropertyDTO> properties) {
        for (PropertyDTO propertyDTO : properties) {
            propertyService.saveProperty(propertyDTO);
        }
    }

    @Operation(summary = "Update a property", description = "Modify details of an existing property by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the property"),
            @ApiResponse(responseCode = "404", description = "Property not found with the provided ID")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PropertyDTO> updateProperty(@PathVariable Long id, @RequestBody PropertyDTO propertyDTO) {
        try {
            PropertyDTO updatedProperty = propertyService.updateProperty(id, propertyDTO);
            return ResponseEntity.ok(updatedProperty);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Delete a property", description = "Remove a property by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the property"),
            @ApiResponse(responseCode = "404", description = "Property not found with the provided ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        if (propertyService.deleteProperty(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}