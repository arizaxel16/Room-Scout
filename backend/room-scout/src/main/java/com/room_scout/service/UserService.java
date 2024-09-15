package com.room_scout.service;

import com.room_scout.controller.dto.UserDTO;
import com.room_scout.model.User;
import com.room_scout.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapEntityToResponseDto)
                .toList();
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapEntityToResponseDto);
    }

    public User saveUser(UserDTO userDTO) {
        User user = mapDtoToEntity(userDTO);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User mapDtoToEntity(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setSurname(dto.surname());
        user.setPassword(dto.password());
        user.setRole(dto.role());
        return user;
    }

    private UserDTO mapEntityToResponseDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getPassword(), 
                user.getRole()
        );
    }
}
