package org.example.constructionmaterialsapi.mapper;

import org.example.constructionmaterialsapi.enums.Role;
import org.example.constructionmaterialsapi.model.dto.request.RegisterRequest;
import org.example.constructionmaterialsapi.model.dto.response.UserResponse;
import org.example.constructionmaterialsapi.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(RegisterRequest request) {
        if (request == null) {
            return null;
        }
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setRole(Role.USER);
        user.setActive(false);
        return user;
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .active(user.isActive())
                .createAt(String.valueOf(user.getCreateAt()))
                .build();
    }
}