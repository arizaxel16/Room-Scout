package com.room_scout.controller;

import com.room_scout.controller.dto.LoginDTO;
import com.room_scout.controller.dto.UserDTO;
import com.room_scout.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000", "http://157.173.114.224:3000" })
@Tag(name = "User Management", description = "API for managing users and authentication")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Retrieve all users", description = "Fetch a list of all registered users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of users"),
            @ApiResponse(responseCode = "204", description = "No users found")
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }
    @Operation(summary = "Retrieve all email", description = "Fetch email by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved email"),
            @ApiResponse(responseCode = "204", description = "User not found with the provided ID")
    })
    @GetMapping("/email/{id}")
    public ResponseEntity<String> getEmailById(@PathVariable Long id) {
        Optional<UserDTO> user = userService.getUserById(id);
        return user.map(userDTO -> ResponseEntity.ok(userDTO.email())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Retrieve a user by ID", description = "Fetch details of a specific user using their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user details"),
            @ApiResponse(responseCode = "404", description = "User not found with the provided ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Delete a user", description = "Remove a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the user"),
            @ApiResponse(responseCode = "404", description = "User not found with the provided ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (!isDeleted)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create a new user", description = "Add a new user to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new user"),
            @ApiResponse(responseCode = "400", description = "Invalid input for creating a user")
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.saveUser(userDTO);
        URI location = URI.create("/users/" + savedUser.id());
        return ResponseEntity.created(location).body(savedUser);
    }

    @Operation(summary = "Update a user", description = "Modify details of an existing user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user"),
            @ApiResponse(responseCode = "404", description = "User not found with the provided ID")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Optional<UserDTO> updatedUser = userService.updateUser(id, userDTO);
        if (updatedUser.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(updatedUser.get());
    }

    @Operation(summary = "User login", description = "Authenticate a user with their credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid login credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<Optional<UserDTO>> loginUser(@RequestBody LoginDTO loginDTO) {
        Optional<UserDTO> userDTO = userService.checkUserLogin(loginDTO);

        if (userDTO.isPresent()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
