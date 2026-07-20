package org.example.constructionmaterialsapi.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.enums.Role;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {

    @NotBlank
    String fullName;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String phone;

    @NotBlank
    String password;

    Role role;
}
