package org.example.constructionmaterialsapi.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.enums.Role;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    Long id;
    String fullName;
    String email;
    String phone;
    boolean active;
    String createAt;
}
