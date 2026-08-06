package org.example.constructionmaterialsapi.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {

    @NotEmpty(message = "Sifarisde en az 1 mehsul olmalidir")
    @Valid
    List<OrderItemRequest> items;

}
