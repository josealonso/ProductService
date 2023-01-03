package com.josealonso.productservice.service;

import com.josealonso.productservice.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto getProductById(UUID uuid);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProductById(UUID productId, ProductDto productDto);

    void deleteProductById(UUID productId);
}
