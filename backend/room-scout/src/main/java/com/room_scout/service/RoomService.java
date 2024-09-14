package com.room_scout.service;
import com.room_scout.model.AccommodationJPA;
import com.room_scout.model.RoomJPA;
import com.room_scout.model.UserJPA;
import com.room_scout.repository.AccommodationORM;
import com.room_scout.repository.RoomORM;
import com.room_scout.repository.UserORM;
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
