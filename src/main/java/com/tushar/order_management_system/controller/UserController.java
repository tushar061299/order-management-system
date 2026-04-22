package com.tushar.order_management_system.controller;

import com.tushar.order_management_system.dto.UserDto;
import com.tushar.order_management_system.dto.UserResponseDto;
import com.tushar.order_management_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.login(userDto));
    }
}
