package org.example.constructionmaterialsapi.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.model.dto.request.UserCreateRequest;
import org.example.constructionmaterialsapi.model.dto.response.UserResponse;
import org.example.constructionmaterialsapi.model.entity.User;
import org.example.constructionmaterialsapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest createRequest) {

        UserResponse createdUser = userService.createUser(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

}
