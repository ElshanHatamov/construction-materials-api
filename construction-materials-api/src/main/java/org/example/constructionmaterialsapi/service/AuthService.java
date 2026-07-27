package org.example.constructionmaterialsapi.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.constructionmaterialsapi.exception.UserAlreadyExistsException;
import org.example.constructionmaterialsapi.exception.UserNotFoundException;
import org.example.constructionmaterialsapi.mapper.UserMapper;
import org.example.constructionmaterialsapi.model.dto.request.LoginRequest;
import org.example.constructionmaterialsapi.model.dto.request.RegisterRequest;
import org.example.constructionmaterialsapi.model.dto.response.LoginResponse;
import org.example.constructionmaterialsapi.model.entity.RefreshToken;
import org.example.constructionmaterialsapi.model.entity.User;
import org.example.constructionmaterialsapi.repository.ProductRepository;
import org.example.constructionmaterialsapi.repository.UserRepository;
import org.example.constructionmaterialsapi.security.jwt.JwtService;
import org.example.constructionmaterialsapi.security.jwt.refresh.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService {

    AuthenticationManager authenticationManager;
    JwtService jwtService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    ProductRepository productRepository;
    UserMapper userMapper;
    RefreshTokenService refreshTokenService;


    public String register(RegisterRequest request) {

        User exsistingUser = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (exsistingUser != null) {

            if (exsistingUser.isActive()) {
                throw new UserAlreadyExistsException("Bu email artiq istifade olunub");
            }
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Bu telefon nomresi artiq istifade olunub");
        }
        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        log.info("Yeni istifadeci qeydiyyatdan kecdi {}", request.getEmail());

        return "Qeydiyyat ugurla tamamlandi";
    }

    public LoginResponse login(LoginRequest loginRequest) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new UserNotFoundException("Istifadeci tapilmadi"));

        String accessToken = jwtService.generateToken(loginRequest.getEmail());
        RefreshToken refreshToken = refreshTokenService.create(user);


        return new LoginResponse(accessToken, refreshToken.getToken(), user.getRole().name());

    }

    public void logout(String refreshToken) {
        refreshTokenService.delete(refreshToken);
    }

    public LoginResponse refresh(String refreshToken) {
        RefreshToken existing = refreshTokenService.validate(refreshToken);
        User user = existing.getUser();

        refreshTokenService.delete(refreshToken);

        String newAccessToken = jwtService.generateToken(user.getEmail());
        RefreshToken newRefreshToken = refreshTokenService.create(user);

        return new LoginResponse(newAccessToken,newRefreshToken.getToken(),user.getRole().name());
    }
}
