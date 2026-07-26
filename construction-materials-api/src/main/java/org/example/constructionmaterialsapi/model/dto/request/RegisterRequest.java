package org.example.constructionmaterialsapi.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Ad və soyad boş ola bilməz")
    String fullName;
    @NotBlank(message = "Email boş ola bilməz")
    @Email(message = "Düzgün email ünvanı daxil edin. Məsələn: Test@gmail.com")
    String email;
    @NotBlank(message = "Telefon nömrəsi boş ola bilməz")
    @Pattern(
            regexp = "^(050|051|055|070|077|010|099)\\d{7}$",
            message = "Telefon nömrəsi düzgün deyil. Məsələn: 0501234567"
    )
    String phone;

    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&._-]).{8,}$",
            message = "Şifrə minimum 8 simvol olmalı, ən azı 1 hərf, 1 rəqəm və 1 xüsusi simvol içerməlidir. Məsələn: Test123!"
    )
    String password;
}
