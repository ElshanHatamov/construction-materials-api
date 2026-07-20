package org.example.constructionmaterialsapi.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.exception.UserAlreadyExistsException;
import org.example.constructionmaterialsapi.mapper.UserMapper;
import org.example.constructionmaterialsapi.model.dto.request.UserCreateRequest;
import org.example.constructionmaterialsapi.model.dto.response.UserResponse;
import org.example.constructionmaterialsapi.model.entity.User;
import org.example.constructionmaterialsapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail((request.getEmail()))) {
            throw new UserAlreadyExistsException("Email already exists: " + request.getEmail());
        }
        if (userRepository.existsByPhone((request.getPhone()))) {
            throw new UserAlreadyExistsException("Phone already exists: " + request.getPhone());
        }

        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }
}
