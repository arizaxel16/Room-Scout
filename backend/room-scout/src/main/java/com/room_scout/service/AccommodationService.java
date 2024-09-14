package com.room_scout.service;
import com.room_scout.model.AccommodationORM;
import com.room_scout.repository.AccommodationJPA;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor

public class AccommodationService
{
    private final AccommodationJPA accommodationJPA;

    public void saveAccommodation(String name, String type, String location, String country, String city) {
        AccommodationORM newAccommodation = new AccommodationORM();
        newAccommodation.setName(name);
        newAccommodation.setType(type);
        newAccommodation.setLocation(location);
        newAccommodation.setCountry(country);
        newAccommodation.setCreation(LocalDate.now());
        newAccommodation.setCity(city);
        accommodationJPA.save(newAccommodation);
    }

    public List<AccommodationORM> fetchAccommodations()
    {
        List<AccommodationORM> list = accommodationJPA.findAll();
        return list;
    }
}
