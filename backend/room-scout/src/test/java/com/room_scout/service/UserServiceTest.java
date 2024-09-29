package com.room_scout.service;

import com.room_scout.controller.dto.LoginDTO;
import com.room_scout.controller.dto.UserDTO;
import com.room_scout.exception.UserAlreadyExistsException;
import com.room_scout.model.User;
import com.room_scout.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private User user;
    private LoginDTO loginDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO(
                1L,
                "john_doe",
                123456789,
                "john@example.com",
                "John",
                "Doe",
                "password123",
                "Admin");

        user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setIdentification(123456789);
        user.setEmail("john@example.com");
        user.setName("John");
        user.setSurname("Doe");
        user.setPassword("password123");
        user.setRole("Admin");

        loginDTO = new LoginDTO("john@example.com", "password123");
    }

    @Test
    void givenValidUser_whenSaveUser_thenUserIsSaved() {
        when(userRepository.existsByEmail(userDTO.email())).thenReturn(false);
        when(userRepository.existsByIdentification(userDTO.identification())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO savedUser = userService.saveUser(userDTO);

        assertNotNull(savedUser);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void givenExistingIdentification_whenSaveUser_thenThrowException() {
        when(userRepository.existsByIdentification(userDTO.identification())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.saveUser(userDTO);
        });

        assertEquals("Identification number already exists", exception.getMessage());
    }

    @Test
    void givenExistingEmail_whenSaveUser_thenThrowException() {
        when(userRepository.existsByEmail(userDTO.email())).thenReturn(true);

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () -> {
            userService.saveUser(userDTO);
        });

        assertEquals("e-mail already exists", exception.getMessage());
    }

    @Test
    void givenValidId_whenGetUserById_thenReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserDTO> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("john_doe", result.get().username());
    }

    @Test
    void givenInvalidId_whenGetUserById_thenReturnEmpty() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<UserDTO> result = userService.getUserById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void givenValidLogin_whenCheckUserLogin_thenReturnUser() {
        when(userRepository.findByEmail(loginDTO.email())).thenReturn(Optional.of(user));

        Optional<UserDTO> result = userService.checkUserLogin(loginDTO);

        assertTrue(result.isPresent());
        assertEquals("john@example.com", result.get().email());
    }

    @Test
    void givenInvalidPassword_whenCheckUserLogin_thenReturnEmpty() {
        loginDTO = new LoginDTO("john@example.com", "wrongpassword");
        when(userRepository.findByEmail(loginDTO.email())).thenReturn(Optional.of(user));

        Optional<UserDTO> result = userService.checkUserLogin(loginDTO);

        assertTrue(result.isEmpty());
    }

    @Test
    void givenValidUser_whenDeleteUser_thenReturnTrue() {
        when(userRepository.existsById(1L)).thenReturn(true);

        boolean isDeleted = userService.deleteUser(1L);

        assertTrue(isDeleted);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void givenInvalidUserId_whenDeleteUser_thenReturnFalse() {
        when(userRepository.existsById(1L)).thenReturn(false);

        boolean isDeleted = userService.deleteUser(1L);

        assertFalse(isDeleted);
        verify(userRepository, never()).deleteById(1L);
    }
}
