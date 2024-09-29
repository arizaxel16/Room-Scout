package com.room_scout.service;

import com.room_scout.controller.dto.LoginDTO;
import com.room_scout.controller.dto.UserDTO;
import com.room_scout.model.User;
import com.room_scout.repository.UserRepository;
import com.room_scout.exception.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO saveUser(UserDTO userDTO) {
        validateUserDTO(userDTO);

        if (userRepository.existsByIdentification(userDTO.identification())) {
            throw new UserAlreadyExistsException("Identification number already exists");
        }

        if (userRepository.existsByEmail(userDTO.email())) {
            throw new UserAlreadyExistsException("e-mail already exists");
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
        return userRepository.findByEmail(loginDTO.email())
                .filter(user -> loginDTO.password().equals(user.getPassword()))
                .map(this::mapEntityToDTO);
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
        validateUserDTO(userDTO);

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

    public UserDTO mapEntityToDTO(User user) {
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

    public User mapDtoToEntity(UserDTO userDTO) {
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

    private void validateUserDTO(UserDTO userDTO) {
        if (userDTO == null || userDTO.username() == null || userDTO.email() == null) {
            throw new IllegalArgumentException("Invalid user data");
        }
    }
}
