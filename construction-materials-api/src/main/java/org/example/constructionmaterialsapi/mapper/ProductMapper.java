package org.example.constructionmaterialsapi.mapper;

import org.example.constructionmaterialsapi.model.dto.request.ProductRequest;
import org.example.constructionmaterialsapi.model.dto.response.ProductResponse;
import org.example.constructionmaterialsapi.model.entity.Category;
import org.example.constructionmaterialsapi.model.entity.Product;
import org.example.constructionmaterialsapi.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    //Create/Post ucun
    public Product toEntity(ProductRequest request, User seller, Category category) {
        if (request == null) return null;

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setUnit(request.getUnit());

        //Relations
        product.setSeller(seller);
        product.setCategory(category);

        return product;
    }

    public ProductResponse toResponse(Product product) {
        if (product == null) return null;

        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setStock(product.getStock());
        productResponse.setUnit(product.getUnit());

        //Satici melumat
        if (product.getSeller() != null) {

            productResponse.setSellerId(product.getSeller().getId());
            productResponse.setSellerName(product.getSeller().getFullName());
        }

        //Kategoriya malumat
        if (product.getCategory() != null) {

            productResponse.setCategoryId(product.getCategory().getId());
            productResponse.setCategoryName(product.getCategory().getName());
        }
        return productResponse;
    }
    //Movcud entitini yenilemek/PUT

    public void updateEntityFromDto(Product product, ProductRequest productRequest, User seller, Category category) {
        if (productRequest == null || product == null) return;

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setUnit(productRequest.getUnit());

        if (seller != null) {
            product.setSeller(seller);
        }
        if (category != null) {
            product.setCategory(category);
        }
    }
}
