package com.room_scout.service;
import com.room_scout.model.UserORM;
import com.room_scout.repository.UserJPA;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class UserService
{
    private final UserJPA userJPA;

    public void saveUser(int identification, String name, String password, String email) {
        UserORM newUser = new UserORM();
        newUser.setIdentification(identification);
        newUser.setName(name);
        newUser.setPassword(password);
        newUser.setEmail(email);
        userJPA.save(newUser);
    }

    public List<UserORM> fetchUsers()
    {
        List<UserORM> list = userJPA.findAll();
        return list;
    }
}
