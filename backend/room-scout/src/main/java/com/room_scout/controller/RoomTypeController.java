package com.room_scout.controller;

import com.room_scout.controller.dto.AddOnDTO;
import com.room_scout.controller.dto.RoomTypeDTO;
import com.room_scout.model.AddOn;
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

    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeDTO> getRoomType(@PathVariable Long id) {
        Optional<RoomTypeDTO> roomTypeDTO = roomTypeService.getRoomTypeById(id);
        return roomTypeDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RoomTypeDTO>> getAllRoomTypes() {
        List<RoomTypeDTO> roomTypes = roomTypeService.getAllRoomTypes();
        return ResponseEntity.ok(roomTypes);
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

    @PutMapping("/{id}")
    public ResponseEntity<RoomType> updateRoomType(@PathVariable Long id, @RequestBody RoomTypeDTO RoomTypeDTO) {
        RoomType updatedRoomType = roomTypeService.updateRoomType(id, RoomTypeDTO);
        return ResponseEntity.ok(updatedRoomType);
    }
}
