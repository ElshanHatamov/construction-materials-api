package org.example.constructionmaterialsapi.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.enums.UnitType;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {

    @Schema(description = "Mehsulun unikal ID_si", example = "5")
    Long id;
    @Schema(description = "Mehsulun adi", example = "Sement")
    String name;
    @Schema(description = "Mehsul etrafli tesviri", example = "Yuksek keyfiyyetli tikinti sementi, 100kg kiselerdedir")
    String description;
    @Schema(description = "Mehsulun qiymeti", example = "15.50")
    BigDecimal price;
    @Schema(description = "Mehsulun stok miqdari", example = "100")
    Integer stock;
    @Schema(description = "Olcu vahidi", example = "KG")
    UnitType unit;

    //Satici
    @Schema(description = "Saticinin ID-si", example = "4")
    Long sellerId;
    @Schema(description = "Saticinin adi ve ya email adresi", example = "Ali ALiyev")
    String sellerName;

    //Kategoriya
    @Schema(description = "Kategoriya ID-si", example = "5")
    Long categoryId;
    @Schema(description = "kategoriyanin adi", example = "Tikinti materiallari")
    String categoryName;
}
