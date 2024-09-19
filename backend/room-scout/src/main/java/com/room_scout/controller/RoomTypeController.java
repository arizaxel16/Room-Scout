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

@RestController
@RequestMapping("/roomtypes")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @GetMapping
    public ResponseEntity<List<RoomTypeDTO>> getAllRoomTypes() {
        List<RoomTypeDTO> roomTypes = roomTypeService.getAllRoomTypes();
        if (roomTypes.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(roomTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeDTO> getRoomTypeById(@PathVariable Long id) {
        Optional<RoomTypeDTO> roomType = roomTypeService.getRoomTypeById(id);
        return roomType.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomType(@PathVariable Long id) {
        boolean isDeleted = roomTypeService.deleteRoomType(id);
        if (!isDeleted) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<RoomTypeDTO> createRoomType(@RequestBody RoomTypeDTO roomTypeDTO) {
        RoomTypeDTO savedRoomType = roomTypeService.saveRoomType(roomTypeDTO);
        URI location = URI.create("/room-types/" + savedRoomType.id());
        return ResponseEntity.created(location).body(savedRoomType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeDTO> updateRoomType(@PathVariable Long id, @RequestBody RoomTypeDTO roomTypeDTO) {
        Optional<RoomTypeDTO> updatedRoomType = roomTypeService.updateRoomType(id, roomTypeDTO);
        if (updatedRoomType.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updatedRoomType.get());
    }
}
