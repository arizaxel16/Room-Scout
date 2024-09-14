package com.room_scout.controller;

import com.room_scout.controller.dto.UserDTO;
import com.room_scout.model.UserJPA;
import com.room_scout.repository.UserORM;
import com.room_scout.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor

public class UserController
{
    private UserService userService;

    @PostMapping(path = "/user2")
    public String saveUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO.identification(), userDTO.name(),userDTO.password(),userDTO.email());
        return "User created";
    }

    @GetMapping(path = "/users")
    public List<UserORM> saveUser() {
        return userService.fetchUsers();
    }
}
