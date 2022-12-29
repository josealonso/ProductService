package com.josealonso.productservice.service;

import com.josealonso.productservice.dto.ProductRequest;
import com.josealonso.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(String productId);

    ProductResponse createProduct(ProductResponse productResponse);

    void updateProduct(String productId, ProductRequest productRequest);

    void deleteProductById(String productId);
}
