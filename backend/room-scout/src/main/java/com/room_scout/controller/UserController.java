package com.room_scout.controller;

import com.room_scout.model.UserJPA;
import com.room_scout.repository.UserORM;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor

public class UserController
{
    UserJPA userJPA;

    @PostMapping(path = "/user/{identification}/{name}/{password}/{email}")
    public String saveUser(@PathVariable Integer identification, @PathVariable String name, @PathVariable String password, @PathVariable String email){
        UserORM newUser = new UserORM();
        newUser.setIdentification(identification);
        newUser.setName(name);
        newUser.setPassword(password);
        newUser.setEmail(email);

        userJPA.save(newUser);
        return "user created";

    }
}
