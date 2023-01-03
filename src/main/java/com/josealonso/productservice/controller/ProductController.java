package com.josealonso.productservice.controller;

import com.josealonso.productservice.dto.ProductDto;
import com.josealonso.productservice.dto.ProductRequest;
import com.josealonso.productservice.dto.ProductResponse;
import com.josealonso.productservice.service.ProductService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@NotNull @PathVariable("productId") UUID productId) {
        return new ResponseEntity<ProductDto>(productService.getProductById(productId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createProduct(@Valid @NotNull @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity updateProduct(@PathVariable("productId") UUID productId, @Valid @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.updateProductById(productId, productDto), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteProduct(@PathVariable("productId") UUID productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

}




