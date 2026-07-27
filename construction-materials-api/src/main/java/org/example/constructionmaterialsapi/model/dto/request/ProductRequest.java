package org.example.constructionmaterialsapi.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.enums.UnitType;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {

    @NotBlank(message = "Materialin adi bos ola bilmez")
    @Size(min = 2, max = 100, message = "Ad 2-100 simvol araliginda olmalidir")
    @Schema(description = "Mehsulun adi", example = "Sement 300")
    String name;

    @Schema(description = "Mehsulun etrafli tesviri", example = "Yuksek keyfiyyetli tikinti sementi, 100kg kiselerdedir")
    String description;

    @NotNull(message = "Qiymet qeyd olunmalidir")
    @Positive(message = "Qiymet musbet eded olmalidir")
    @Schema(description = "Mehsulun qiymeti", example = "15.50")
    BigDecimal price;
    @NotNull(message = "Stok miqdari bos ola bilmez")
    @Min(value = 0, message = "Stok menfi ola bilmez")
    @Schema(description = "Mehsulun stok miqdari", example = "200")
    Integer stock;

    @NotNull(message = "Olcu vahidi secilmelidir")
    @Schema(description = "Olcu vahidi", example = "KG")
    UnitType unit;

    @NotNull(message = "Kateqoriya ID-si mutleqdir")
    @Schema(description = "Mehsulun aid oldugu kategoriyanin ID-si", example = "1")
    Long categoryId;

}
