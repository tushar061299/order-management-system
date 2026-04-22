package com.tushar.order_management_system.service;

import com.tushar.order_management_system.config.JwtUtil;
import com.tushar.order_management_system.dto.UserDto;
import com.tushar.order_management_system.dto.UserResponseDto;
import com.tushar.order_management_system.entity.User;
import com.tushar.order_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    public UserDto createUser(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        user.setRole("USER");
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser,UserDto.class);
    }

    //login
    public UserResponseDto login(UserDto userDto){
        User existingUser = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(()-> new RuntimeException("User Not Found"));

        if(!existingUser.getPassword().equals(userDto.getPassword())){
            throw new RuntimeException("Invalid Password");
        }

        String token = jwtUtil.generateToken(existingUser.getEmail());
        return new UserResponseDto(userDto.getEmail(), token);

    }

}
