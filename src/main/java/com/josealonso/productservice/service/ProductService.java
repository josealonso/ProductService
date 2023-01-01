package com.josealonso.productservice.service;

import com.josealonso.productservice.dto.ProductRequest;
import com.josealonso.productservice.dto.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductResponse> getAllProducts();

    ProductResponse createProduct(ProductResponse productResponse);

    void updateProductById(UUID productId, ProductRequest productRequest);

    ProductResponse getProductById(UUID uuid);

    void deleteProductById(UUID productId);
}
