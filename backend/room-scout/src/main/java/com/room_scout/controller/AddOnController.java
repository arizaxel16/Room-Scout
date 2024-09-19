package com.room_scout.controller;

import com.room_scout.controller.dto.AddOnDTO;
import com.room_scout.service.AddOnService;
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
@CrossOrigin(origins = {"http://localhost:3000", "http://157.173.114.224:3000"})  // Replace with your frontend IP or domain
public class AddOnController {

    private final AddOnService addOnService;

    @GetMapping
    public ResponseEntity<List<AddOnDTO>> getAllAddOns() {
        List<AddOnDTO> addOns = addOnService.getAllAddOns();
        if (addOns.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(addOns);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddOnDTO> getAddOnById(@PathVariable Long id) {
        Optional<AddOnDTO> addOn = addOnService.getAddOnById(id);
        return addOn.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<AddOnDTO> createAddOn(@RequestBody AddOnDTO addOnDTO) {
        AddOnDTO savedAddOn = addOnService.saveAddOn(addOnDTO);
        URI location = URI.create("/addons/" + savedAddOn.id());
        return ResponseEntity.created(location).body(savedAddOn);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddOnDTO> updateAddOn(@PathVariable Long id, @RequestBody AddOnDTO addOnDTO) {
        Optional<AddOnDTO> updatedAddOn = addOnService.updateAddOn(id, addOnDTO);
        if (updatedAddOn.isPresent()) {
            return ResponseEntity.ok(updatedAddOn.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddOn(@PathVariable Long id) {
        boolean isDeleted = addOnService.deleteAddOn(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}
