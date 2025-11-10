package com.example.lms.mapper;

import com.example.lms.dto.CreateUserRequest;
import com.example.lms.dto.UpdateUserRequest;
import com.example.lms.dto.UserResponse;
import com.example.lms.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        return user;
    }

    public User toEntity(UpdateUserRequest request, User existingUser) {
        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());
        existingUser.setRole(request.getRole());
        return existingUser;
    }

    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }
}
