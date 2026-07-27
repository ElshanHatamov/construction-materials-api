package org.example.constructionmaterialsapi.model.dto.response;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String Role) {
}
