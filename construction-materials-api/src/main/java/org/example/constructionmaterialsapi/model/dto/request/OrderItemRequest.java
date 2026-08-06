package org.example.constructionmaterialsapi.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemRequest {

    @NotNull(message = "Mehsul ID-si bos ola bilmez")
    Long productId;

    @NotNull(message = "Miqdar bos ola bilmez")
    @Positive(message = "Miqdar 0-dan böyük olmalıdır")
    Integer quantity;
}
