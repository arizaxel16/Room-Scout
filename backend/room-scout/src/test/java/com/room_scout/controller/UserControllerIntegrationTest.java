package com.room_scout.controller;

import com.room_scout.controller.dto.UserDTO;
import com.room_scout.model.User;
import com.room_scout.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {

        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setIdentification(123456);
        testUser.setEmail("testuser@example.com");
        testUser.setName("Test");
        testUser.setSurname("User");
        testUser.setPassword("password123");
        testUser.setRole("ROLE_USER");
        userRepository.save(testUser);

        testUserDTO = new UserDTO(
                testUser.getId(),
                testUser.getUsername(),
                testUser.getIdentification(),
                testUser.getEmail(),
                testUser.getName(),
                testUser.getSurname(),
                testUser.getPassword(),
                testUser.getRole());
    }

    @Test
    void shouldCreateUser() throws Exception {

        String newUserJson = """
                    {
                        "username": "newuser",
                        "identification": 654321,
                        "email": "newuser@example.com",
                        "name": "New",
                        "surname": "User",
                        "password": "newpassword",
                        "role": "ROLE_USER"
                    }
                """;

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.email").value("newuser@example.com"));

        User savedUser = userRepository.findByEmail("newuser@example.com").orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("newuser");
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNotEmpty());
    }

    @Test
    void shouldGetUserById() throws Exception {
        mockMvc.perform(get("/users/" + testUserDTO.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testUserDTO.username()));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        String updatedUserJson = """
                    {
                        "username": "updateduser",
                        "identification": 123456,
                        "email": "updated@example.com",
                        "name": "Updated",
                        "surname": "User",
                        "password": "newpassword123",
                        "role": "ROLE_ADMIN"
                    }
                """;

        mockMvc.perform(put("/users/" + testUserDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updateduser"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));

        User updatedUser = userRepository.findByEmail("updated@example.com").orElse(null);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getUsername()).isEqualTo("updateduser");
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/" + testUserDTO.id()))
                .andExpect(status().isNoContent());

        assertThat(userRepository.findById(testUserDTO.id())).isEmpty();
    }

    @Test
    void shouldLoginUser() throws Exception {

        String loginJson = """
                    {
                        "email": "testuser@example.com",
                        "password": "password123"
                    }
                """;

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.email").value("testuser@example.com"));
    }

    @Test
    void shouldFailLoginWithIncorrectPassword() throws Exception {
        String loginJson = """
                    {
                        "email": "testuser@example.com",
                        "password": "wrongpassword"
                    }
                """;

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isUnauthorized());
    }
}
