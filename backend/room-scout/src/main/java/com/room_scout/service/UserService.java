package com.room_scout.service;

import com.room_scout.controller.dto.LoginDTO;
import com.room_scout.controller.dto.UserDTO;
import com.room_scout.model.User;
import com.room_scout.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO saveUser(UserDTO userDTO) {
        if (userRepository.existsByIdentification(userDTO.identification())) {
            throw new IllegalArgumentException("Identification number already exists");
        }

        if (userRepository.existsByEmail(userDTO.email())) {
            throw new IllegalArgumentException("e-mail already exists");
        }

        User user = mapDtoToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return mapEntityToDTO(savedUser);
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapEntityToDTO);
    }

    public Optional<UserDTO> checkUserLogin(LoginDTO loginDTO) {
        Optional<User> userOptional = userRepository.findByEmail(loginDTO.email());
    
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (loginDTO.password().equals(user.getPassword())) {
                return Optional.of(mapEntityToDTO(user));
            }
        }
        return Optional.empty();
    }
    
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapEntityToDTO)
                .toList();
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(userDTO.username());
                    existingUser.setIdentification(userDTO.identification());
                    existingUser.setEmail(userDTO.email());
                    existingUser.setName(userDTO.name());
                    existingUser.setSurname(userDTO.surname());
                    existingUser.setPassword(userDTO.password());
                    existingUser.setRole(userDTO.role());
                    userRepository.save(existingUser);
                    return mapEntityToDTO(existingUser);
                });
    }

    private UserDTO mapEntityToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getIdentification(),
                user.getEmail(),
                user.getName(),
                user.getSurname(),
                user.getPassword(),
                user.getRole()
        );
    }

    private User mapDtoToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.username());
        user.setIdentification(userDTO.identification()); 
        user.setEmail(userDTO.email());
        user.setName(userDTO.name());
        user.setSurname(userDTO.surname());
        user.setPassword(userDTO.password());
        user.setRole(userDTO.role());
        return user;
    }
}

