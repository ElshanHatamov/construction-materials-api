package org.example.constructionmaterialsapi.model.dto.request;

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
    String name;

    String description;

    @NotNull(message = "Qiymet qeyd olunmalidir")
    @Positive(message = "Qiymet musbet eded olmalidir")
    BigDecimal price;
    @NotNull(message = "Stok miqdari bos ola bilmez")
    @Min(value = 0, message = "Stok menfi ola bilmez")
    Integer stock;

    @NotNull(message = "Olcu vahidi secilmelidir")
    UnitType unit;

    @NotNull(message = "Satici ID-si mutleqdir")
    Long seller_id;

    @NotNull(message = "Kateqoriya ID-si mutleqdir")
    Long categoryId;

}
