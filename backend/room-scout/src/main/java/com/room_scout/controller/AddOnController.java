package com.room_scout.controller;

import com.room_scout.controller.dto.AddOnDTO;
import com.room_scout.service.AddOnService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/addons")
@AllArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000", "http://157.173.114.224:3000" })
@Tag(name = "Add-On Management", description = "API to manage property add-ons")
public class AddOnController {

    private final AddOnService addOnService;

    @Operation(summary = "Retrieve all add-ons", description = "Fetch a list of all available add-ons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of add-ons"),
            @ApiResponse(responseCode = "204", description = "No add-ons available")
    })
    @GetMapping
    public ResponseEntity<List<AddOnDTO>> getAllAddOns() {
        List<AddOnDTO> addOns = addOnService.getAllAddOns();
        if (addOns.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(addOns);
    }

    @Operation(summary = "Retrieve a specific add-on by ID", description = "Fetch details of a specific add-on using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved add-on details"),
            @ApiResponse(responseCode = "404", description = "Add-on not found with the provided ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddOnDTO> getAddOnById(@PathVariable Long id) {
        Optional<AddOnDTO> addOn = addOnService.getAddOnById(id);
        return addOn.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Create a new add-on", description = "Add a new add-on to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new add-on"),
            @ApiResponse(responseCode = "400", description = "Invalid input to create add-on")
    })
    @PostMapping
    public ResponseEntity<AddOnDTO> createAddOn(@RequestBody AddOnDTO addOnDTO) {
        AddOnDTO savedAddOn = addOnService.saveAddOn(addOnDTO);
        URI location = URI.create("/addons/" + savedAddOn.id());
        return ResponseEntity.created(location).body(savedAddOn);
    }

    @Operation(summary = "Update an existing add-on", description = "Modify details of an existing add-on by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the add-on"),
            @ApiResponse(responseCode = "404", description = "Add-on not found with the provided ID")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AddOnDTO> updateAddOn(@PathVariable Long id, @RequestBody AddOnDTO addOnDTO) {
        Optional<AddOnDTO> updatedAddOn = addOnService.updateAddOn(id, addOnDTO);
        if (updatedAddOn.isPresent()) {
            return ResponseEntity.ok(updatedAddOn.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Delete an add-on", description = "Remove an add-on from the system by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the add-on"),
            @ApiResponse(responseCode = "404", description = "Add-on not found with the provided ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddOn(@PathVariable Long id) {
        boolean isDeleted = addOnService.deleteAddOn(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}
