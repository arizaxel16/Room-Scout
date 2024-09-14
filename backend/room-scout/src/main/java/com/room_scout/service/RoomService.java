package com.room_scout.service;
import com.room_scout.model.AccommodationORM;
import com.room_scout.model.RoomORM;
import com.room_scout.model.UserORM;
import com.room_scout.repository.AccommodationJPA;
import com.room_scout.repository.RoomJPA;
import com.room_scout.repository.UserJPA;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class RoomService
{
    private final RoomJPA roomJPA;
    private final AccommodationJPA accommodationJPA;
    public void saveRoom(long hotelId, int quantity, String description, String type) {
        AccommodationORM accommodation = accommodationJPA.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
        RoomORM newRoom = new RoomORM();
        newRoom.setHotelId(hotelId);
        newRoom.setQuantity(quantity);
        newRoom.setDescription(description);
        newRoom.setType(type);
        roomJPA.save(newRoom);
    }

    public List<RoomORM> fetchRooms()
    {
        List<RoomORM> list = roomJPA.findAll();
        return list;
    }
}
