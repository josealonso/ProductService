package com.josealonso.productservice.service;

import com.josealonso.productservice.dto.ProductRequest;
import com.josealonso.productservice.dto.ProductResponse;
import com.josealonso.productservice.domain.Product;
import com.josealonso.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductResponse productRequest) {
        Product product = Product.builder()
                .productName(productRequest.getName())
                .productStyle(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} has been saved", product.getId());
        return mapToProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        var products = productRepository.findAll();
        return products.stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getProductName())
                .description(product.getProductStyle())
                .price(product.getPrice())
                .build();
    }

    @Override
    public ProductResponse getProductById(UUID uuid) {
        var product = productRepository.findById(uuid).orElseThrow();
        return mapToProductResponse(product);
    }

    @Override
    public void deleteProductById(UUID productId) {
    }

    @Override
    public void updateProductById(UUID productId, ProductRequest productRequest) {
    }
}
