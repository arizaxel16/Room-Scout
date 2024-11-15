package com.room_scout.controller;

import com.room_scout.controller.dto.RoomTypeDTO;
import com.room_scout.service.RoomTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/roomtypes")
@AllArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000", "http://157.173.114.224:3000" })
@Tag(name = "Room Type Management", description = "API for managing room types")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @Operation(summary = "Retrieve all room types", description = "Fetch a list of all available room types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of room types"),
            @ApiResponse(responseCode = "204", description = "No room types found")
    })
    @GetMapping
    public ResponseEntity<List<RoomTypeDTO>> getAllRoomTypes() {
        List<RoomTypeDTO> roomTypes = roomTypeService.getAllRoomTypes();
        if (roomTypes.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(roomTypes);
    }

    @Operation(summary = "Retrieve a room type by ID", description = "Fetch details of a specific room type using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved room type details"),
            @ApiResponse(responseCode = "404", description = "Room type not found with the provided ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeDTO> getRoomTypeById(@PathVariable Long id) {
        Optional<RoomTypeDTO> roomType = roomTypeService.getRoomTypeById(id);
        return roomType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Delete a room type", description = "Remove a room type by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the room type"),
            @ApiResponse(responseCode = "404", description = "Room type not found with the provided ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomType(@PathVariable Long id) {
        boolean isDeleted = roomTypeService.deleteRoomType(id);
        if (!isDeleted)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create a new room type", description = "Add a new room type to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new room type"),
            @ApiResponse(responseCode = "400", description = "Invalid input for creating a room type")
    })
    @PostMapping
    public ResponseEntity<RoomTypeDTO> createRoomType(@RequestBody RoomTypeDTO roomTypeDTO) {
        RoomTypeDTO savedRoomType = roomTypeService.saveRoomType(roomTypeDTO);
        URI location = URI.create("/roomtypes/" + savedRoomType.id());
        return ResponseEntity.created(location).body(savedRoomType);
    }

    @Operation(summary = "Bulk create room types", description = "Add multiple room types at once")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created room types in bulk"),
            @ApiResponse(responseCode = "400", description = "Invalid input for creating room types")
    })
    @PostMapping("/bulk")
    public void createRoomTypes(@RequestBody List<RoomTypeDTO> roomTypes) {
        for (RoomTypeDTO roomTypeDTO : roomTypes) {
            roomTypeService.saveRoomType(roomTypeDTO);
        }
    }

    @Operation(summary = "Update a room type", description = "Modify details of an existing room type by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the room type"),
            @ApiResponse(responseCode = "404", description = "Room type not found with the provided ID")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeDTO> updateRoomType(@PathVariable Long id, @RequestBody RoomTypeDTO roomTypeDTO) {
        Optional<RoomTypeDTO> updatedRoomType = roomTypeService.updateRoomType(id, roomTypeDTO);
        if (updatedRoomType.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updatedRoomType.get());
    }
}
