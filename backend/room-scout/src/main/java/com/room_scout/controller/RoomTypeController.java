package com.room_scout.controller;

import com.room_scout.controller.dto.RoomTypeDTO;
import com.room_scout.model.RoomType;
import com.room_scout.service.RoomTypeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room-types")
@AllArgsConstructor
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @GetMapping
    public ResponseEntity<List<RoomType>> getAllRoomTypes() {
        return ResponseEntity.ok(roomTypeService.getAllRoomTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomType> getRoomTypeById(@PathVariable Long id) {
        Optional<RoomType> roomType = roomTypeService.getRoomTypeById(id);
        return roomType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomType(@PathVariable Long id) {
        roomTypeService.deleteRoomType(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<RoomType> createRoomType(@RequestBody RoomTypeDTO roomTypeDTO) {
        RoomType savedRoomType = roomTypeService.saveRoomType(roomTypeDTO);
        return ResponseEntity.ok(savedRoomType);
    }
}
