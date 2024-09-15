package com.room_scout.controller;

import com.room_scout.controller.dto.AddOnDTO;
import com.room_scout.model.AddOn;
import com.room_scout.service.AddOnService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/add-ons")
@AllArgsConstructor
public class AddOnController {

    @Autowired
    private AddOnService addOnService;

    @GetMapping
    public ResponseEntity<List<AddOnDTO>> getAllAddOns() {
        return ResponseEntity.ok(addOnService.getAllAddOns());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddOnDTO> getAddOnById(@PathVariable Long id) {
        Optional<AddOnDTO> addOnDTO = addOnService.getAddOnById(id);
        return addOnDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddOn(@PathVariable Long id) {
        addOnService.deleteAddOn(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<AddOn> createAddOn(@RequestBody AddOnDTO addOnDTO) {
        AddOn savedAddOn = addOnService.saveAddOn(addOnDTO);
        return ResponseEntity.ok(savedAddOn);
    }
}
