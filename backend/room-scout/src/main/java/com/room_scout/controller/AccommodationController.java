package com.room_scout.controller;

import com.room_scout.controller.dto.AccommodationDTO;
import com.room_scout.repository.AccommodationORM;
import com.room_scout.service.AccommodationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor

public class AccommodationController
{
    private AccommodationService accommodationService;

    @PostMapping(path = "/accommodation")
    public String saveAccommodation(@RequestBody AccommodationDTO accommodationDTO) {
        accommodationService.saveAccommodation(accommodationDTO.name(),accommodationDTO.type(),accommodationDTO.location(),accommodationDTO.country(),accommodationDTO.city());
        return "Accommodation created";
    }

    @GetMapping(path = "/accommodations")
    public List<AccommodationORM> saveAccommodation() {
        return accommodationService.fetchAccommodations();
    }
}
