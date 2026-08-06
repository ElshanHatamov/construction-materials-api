package org.example.constructionmaterialsapi.security.jwt.refresh;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.exception.TokenExpiredException;
import org.example.constructionmaterialsapi.model.entity.RefreshToken;
import org.example.constructionmaterialsapi.model.entity.User;
import org.example.constructionmaterialsapi.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RefreshTokenService {

    RefreshTokenRepository refreshTokenRepository;

    public RefreshToken create(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshToken.setCreatedAt(Instant.now());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken validate(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token tapilmadi"));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.deleteByToken(token);
            throw new TokenExpiredException("Refresh token muddeti bitib. Zehmet olmasa yeniden daxil olun");
        }
        return refreshToken;
    }

    @Transactional
    public void deleteAllByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    public void delete(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
