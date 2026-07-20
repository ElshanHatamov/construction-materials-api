package org.example.constructionmaterialsapi.mapper;

import org.example.constructionmaterialsapi.model.dto.request.UserCreateRequest;
import org.example.constructionmaterialsapi.model.dto.response.UserResponse;
import org.example.constructionmaterialsapi.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserCreateRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setActive(true);
        return user;
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .active(user.isActive())
                .role(user.getRole())
                .createAt(user.getCreateAt())
                .build();
    }
}