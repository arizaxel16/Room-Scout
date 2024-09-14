package com.room_scout.controller;

import com.room_scout.controller.dto.RoomDTO;
import com.room_scout.controller.dto.UserDTO;
import com.room_scout.repository.RoomORM;
import com.room_scout.repository.UserORM;
import com.room_scout.service.RoomService;
import com.room_scout.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor

public class RoomController
{
    private RoomService roomService;

    @PostMapping(path = "/room")
    public String saveRoom(@RequestBody RoomDTO roomDTO) {
        roomService.saveRoom(roomDTO.hotelId(),roomDTO.quantity(),roomDTO.description(),roomDTO.type());
        return "Room created";
    }

    @GetMapping(path = "/rooms")
    public List<RoomORM> saveRoom() {
        return roomService.fetchRooms();
    }
}
