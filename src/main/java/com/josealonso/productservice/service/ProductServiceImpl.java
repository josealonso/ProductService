package com.josealonso.productservice.service;

import com.josealonso.productservice.controller.NotFoundException;
import com.josealonso.productservice.dto.ProductDto;
import com.josealonso.productservice.dto.ProductRequest;
import com.josealonso.productservice.dto.ProductResponse;
import com.josealonso.productservice.domain.Product;
import com.josealonso.productservice.mappers.ProductMapper;
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
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> getAllProducts() {
        var products = productRepository.findAll();
        return products.stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(UUID uuid) {
        var product = productRepository.findById(uuid).orElseThrow(NotFoundException::new);
        return productMapper.productToProductDto(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        productRepository.save(product);
        log.info("Product {} has been saved", product.getId());
        return productMapper.productToProductDto(product);
    }

    @Override
    public ProductDto updateProductById(UUID productId, ProductDto productDto) {
        Product productToBeUpdated = productRepository.findById(productId).orElseThrow(NotFoundException::new);

        productToBeUpdated.setProductName(productDto.getName());
        productToBeUpdated.setProductStyle(productDto.getStyle());
        productToBeUpdated.setPrice(productDto.getPrice());
        productToBeUpdated.setUpc(productDto.getUpc());

        return productMapper.productToProductDto(productRepository.save(productToBeUpdated));
    }

    @Override
    public void deleteProductById(UUID productId) {
        Product productToBeDeleted = productRepository.findById(productId).orElseThrow(NotFoundException::new);
        productRepository.delete(productToBeDeleted);
    }

}
