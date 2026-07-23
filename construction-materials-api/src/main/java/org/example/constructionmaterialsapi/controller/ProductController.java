package org.example.constructionmaterialsapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.constructionmaterialsapi.model.dto.request.ProductRequest;
import org.example.constructionmaterialsapi.model.dto.response.ProductResponse;
import org.example.constructionmaterialsapi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Product Controller", description = "Mehsullarin idare olunmasi ucun API endpoint-leri")
public class ProductController {

    ProductService productService;

    @Operation(
            summary = "Yeni mehsul yaradir",
            description = "Daxil olmus istifadeci yeni mehsul yarada bilir"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mehsul ugurla yaradildi"),
            @ApiResponse(responseCode = "400", description = "Daxil edilen melumatlar yanlisdir(Validation xetasi"),
            @ApiResponse(responseCode = "403", description = "Mehsul yaratmaq ucun giris etmelisiniz"),
            @ApiResponse(responseCode = "404", description = "Daxil edilen kategoriya tapilmadi")
    })
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request,
                                                         @RequestParam String ownerEmail) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(
                        request, ownerEmail
                ));
    }

    @Operation(
            summary = "ID-ye uygun melumati elde edir",
            description = "Istifadeci mehsulun id-sini daxil ederek melumat elde edir"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mehsul tapildi ve melumatlari qaytarildi"),
            @ApiResponse(responseCode = "404", description = "Daxil edilen ID-ye uygun mehsul tapilmadi")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(
            summary = "ID-ye gore mehsulu silir",
            description = "Daxil olmus istifadeci yalniz ozune aid olan mehsulu sile biler"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mehsul ugurla silindi"),
            @ApiResponse(responseCode = "403", description = "Bu mehsulu silmek ucun icazeniz yoxdur"),
            @ApiResponse(responseCode = "404", description = "Mehsul tapilmadi")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id,
                                                  @RequestParam String ownerEmail) {
        productService.deleteProductById(id, ownerEmail);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Butun melumatlarin siyahisini alir",
            description = "Mehsullari sehifelenmis (Pagination) seklinde qaytarir. Susmaya gore sehifede 10 mehsul olur ve ID-ye gore cesidlenir"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mehsullarin siyahisi ugurla alindi")
    })
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @Operation(
            summary = "Mehsul melumatlarini yenileyir",
            description = "Daxil olmus istifadeci yalniz ozune aid olan mehsulun melumatlarini yenileye biler"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mehsul ugurla yenilendi"),
            @ApiResponse(responseCode = "400", description = "Daxil edilen melumatlar yanlisdir(Validation xetasi"),
            @ApiResponse(responseCode = "403", description = "Bu mehsulu yenilemek ucun icazeniz yoxdur"),
            @ApiResponse(responseCode = "404", description = "Mehsul veya Kategoriya tapilmadi")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @Valid @RequestBody ProductRequest request,
                                                         @RequestParam String ownerEmail) {
        return ResponseEntity.ok(productService.updateProduct(id, request, ownerEmail));
    }

}
