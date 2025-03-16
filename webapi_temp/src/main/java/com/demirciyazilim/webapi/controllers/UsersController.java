package com.demirciyazilim.webapi.controllers;

import com.demirciyazilim.business.abstracts.UserService;
import com.demirciyazilim.core.utilities.results.DataResult;
import com.demirciyazilim.core.utilities.results.Result;
import com.demirciyazilim.entities.User;
import com.demirciyazilim.webapi.models.ChangePasswordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Tag(name = "User", description = "User API")
public class UsersController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns all users")
    public ResponseEntity<DataResult<List<User>>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", description = "Returns user by id")
    public ResponseEntity<DataResult<User>> getById(@PathVariable Long id) {
        DataResult<User> result = userService.getById(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Get user by username", description = "Returns user by username")
    public ResponseEntity<DataResult<User>> getByUsername(@PathVariable String username) {
        DataResult<User> result = userService.getByUsername(username);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Returns user by email")
    public ResponseEntity<DataResult<User>> getByEmail(@PathVariable String email) {
        DataResult<User> result = userService.getByEmail(email);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping
    @Operation(summary = "Add user", description = "Adds a new user")
    public ResponseEntity<DataResult<User>> add(@RequestBody User user) {
        DataResult<User> result = userService.add(user);
        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @PutMapping
    @Operation(summary = "Update user", description = "Updates an existing user")
    public ResponseEntity<DataResult<User>> update(@RequestBody User user) {
        DataResult<User> result = userService.update(user);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by id")
    public ResponseEntity<Result> delete(@PathVariable Long id) {
        Result result = userService.delete(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PatchMapping("/activate/{id}")
    @Operation(summary = "Activate user", description = "Activates a user by id")
    public ResponseEntity<Result> activate(@PathVariable Long id) {
        Result result = userService.activate(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PatchMapping("/deactivate/{id}")
    @Operation(summary = "Deactivate user", description = "Deactivates a user by id")
    public ResponseEntity<Result> deactivate(@PathVariable Long id) {
        Result result = userService.deactivate(id);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @PatchMapping("/change-password")
    @Operation(summary = "Change password", description = "Changes user password")
    public ResponseEntity<Result> changePassword(@RequestBody ChangePasswordRequest request) {
        Result result = userService.changePassword(request.getUserId(), request.getOldPassword(), request.getNewPassword());
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @PatchMapping("/update-last-login/{username}")
    @Operation(summary = "Update last login", description = "Updates user's last login time")
    public ResponseEntity<Result> updateLastLogin(@PathVariable String username) {
        Result result = userService.updateLastLogin(username);
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/exists/username/{username}")
    @Operation(summary = "Check if username exists", description = "Checks if username exists")
    public ResponseEntity<Result> existsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.existsByUsername(username));
    }

    @GetMapping("/exists/email/{email}")
    @Operation(summary = "Check if email exists", description = "Checks if email exists")
    public ResponseEntity<Result> existsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }
} 